using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DLboard = IntroSE.Kanban.Backend.DataAccessLayer.Objects.Board;
using DlColumn = IntroSE.Kanban.Backend.DataAccessLayer.Objects.Column;
using IntroSE.Kanban.Backend.DataAccessLayer;

namespace IntroSE.Kanban.Backend.BusinessLayer
{

    abstract class Board:PersistedObject<DLboard>
    {
       
        private const string firstColumnName = "backlog";
        private const string SecondColumnName = "in progress";
        private const string ThirdColumnName = "done";
        private const int DefaultLimit = 100;
        protected Dictionary<int, Column> columns;
        protected Int32 taskIdCounter;
        protected string creatorEmail;
        public string CreatorEmail { get => creatorEmail; set { creatorEmail = value; } }
        internal int TaskIdCounter { get => taskIdCounter; set { ToDalObject().maxIdCounter = value;taskIdCounter = value; } }//Id counter for tasks.
        protected static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        /// <summary>
        /// Creates a Board, and create 3 columns with the names required. 
        /// </summary>
        internal Board(string email)
        {
            taskIdCounter = 0;

            creatorEmail = email;
            columns = new Dictionary<int, Column>();
            DLboard dlb = new DLboard(this.TaskIdCounter);
            dlb.Insert();
            this.id = dlb.Id;
            columns.Add(0, new Column(firstColumnName, DefaultLimit, 0, id));
            columns.Add(1, new Column(SecondColumnName, DefaultLimit, 1, id));
            columns.Add(2, new Column(ThirdColumnName, DefaultLimit, 2, id));
        }

        //Empty constructor
        internal Board() { }

        //Copy constructor.
        internal Board(Board other) {
            this.id = other.GetDataBaseID();
            this.columns = other.GetColumns();
            this.taskIdCounter = other.TaskIdCounter;
            this.CreatorEmail = other.CreatorEmail;
        }
       
        /// <summary>
        /// Assigns a new email to an existing task in the board.
        /// </summary>
        /// <param name="email">the email of the user that requests the change</param>
        /// <param name="columnOrdinal">The ordinal number of the column that contains the task</param>
        /// <param name="taskId">the task id</param>
        /// <param name="emailAssignee">The new email to set as the task's email assignee</param>
        internal void AssignTask(string email, int columnOrdinal, int taskId, string emailAssignee)
        {
            CheckIfCanChangeInColumn(columnOrdinal);
            GetColumn(columnOrdinal).AssignTask(email, taskId, emailAssignee);
        }

        /// <summary>
        /// Checks if the column is the last column, if so throws an exception 
        /// because the tasks in the last column cant  be changed.
        /// </summary>
        /// <param name="columnOrdinal"></param>
        private void CheckIfCanChangeInColumn(int columnOrdinal)
        {
            if (columnOrdinal<0 || columnOrdinal >= GetMaxColumnId())
            {
                string errorMsg = "Cannot change the last column or the column ordinal is out of bounds";
                log.Error(errorMsg);
                throw new Exception(errorMsg);
            }

        }

        //Returns the last added task's id
        internal int GetMaxTasksId() { return TaskIdCounter; }

        /// <summary>
        /// Constructor for  data loading 
        /// creating new boards with the data from the data base.
        /// </summary>
        /// <param name="dlb">the data layer board</param>
        /// <param name="dlColumns">all of the columns of the board as data layer columns</param>
       internal Board(DLboard dlb,Dictionary<int,Column> bColumns)
        {
            this.id = dlb.Id;
            this.taskIdCounter = (int)dlb.maxIdCounter;
            columns = bColumns;

            /// <summary>
            ///adding a new column to the Board
            /// if neccessery moving all the columns to the right in order to make room for the new column
            /// update the new column in the columns table in the data
            /// </summary>
            /// <param name="cOrdinal">the spot for new new column</param>
            /// <param name="name">the new column's name</param>
        }

       //Returns data base id.
        internal long GetDataBaseID()
        {
            return this.id;
        }

        /// <summary>
        /// Adding a new column at the requested ordinal number.
        /// </summary>
        /// <param name="cOrdianl">The ordinal number to place the column at</param>
        /// <param name="name"> the name of the new column</param>
        public virtual void AddColumn(int cOrdianl,string name)
        {   
            
            if(cOrdianl>GetMaxColumnId()+1 || cOrdianl < 0)
            {
                string err = "The index of the new column is out of bounds";
                log.Error(err);
                throw new Exception(err);
            }
            if (IsColumnNameExists(name))
            {
                log.Warn("The column named: " + name + " cant be added because this name exists");
                throw new Exception("This column name already exists");
            }
            Column toAdd = new Column(name, DefaultLimit,cOrdianl,this.id);
            if(cOrdianl==GetMaxColumnId()+1)
                columns.Add(cOrdianl, toAdd);
            else
            {
                //Adding one to each column's id (the key in the dictionary), from columnId 'cOrdinal' and on
                columns.Add(GetMaxColumnId()+1, null);
                for(int i = GetMaxColumnId(); i > cOrdianl; i--)
                {
                    columns[i] = columns[i - 1];
                }
                columns[cOrdianl] = toAdd;
            }
            UpdateColumnsColumnId();//Updating the new ColumId fields in the data base.

        }
       
        /// <summary>
        /// Checks if a name already exists in the columns dictionary (not case sensitive).
        /// deleting the column from the board in the data base.
        /// </summary>
        /// <param name="cName">the name to check if exists.</param>
        /// <returns>true if the name exists, false else.</returns>
        private bool IsColumnNameExists(string cName)
        {
            
            foreach(Column c in columns.Values)
            {
                if (c.GetName()== cName)
                    return true;
            }
            return false;
        }
       
        /// <summary>
        ///deleting a column from the Board
        /// if neccessery moving all the columns to the left in order to maintain the perssitency for the columns numbers.
        /// moving its tasks to the left columns, unless it is the most left columns - then transfer the tasks to the right
        /// updates the changes in the columns table in the data
        /// </summary>
        /// <param name="cOrdinal">the place of the column to delete</param>
        public virtual void RemoveColumn(int columnOrdinal)
        {

            if (!columns.ContainsKey(columnOrdinal))
            {
                log.Error("The column you are trying to delete doesn't exists. Column Key: " + columnOrdinal);
                throw new Exception("This column cannot be removed because it doesn't exist. Column Ordinal: " + columnOrdinal);
            }
            if (columns.Count == 2)
            {
                log.Warn("Cant delete the column because there are not enough columns to do so");
                throw new Exception("This column cannot be removed because there are only 2 columns left");
            }

            int maxId = GetMaxColumnId();


            Column deletedColumn = columns[columnOrdinal];
            Column toTransfer = null;//The column we want to transfer the tasks to. (right if we want to delete the leftmost column, or right elseway)

            int toTransferOrdinal = 1;
            if (columnOrdinal != 0) {
                toTransfer = columns[columnOrdinal - 1];
                toTransferOrdinal = columnOrdinal - 1;
            }
            else
                toTransfer = columns[1];

            int sumOfTasks = toTransfer.GetNumOfTasks() + deletedColumn.GetNumOfTasks();//the sum of both columns tasks.
            int toTransferLimit = toTransfer.GetLimit();//the limit of the new column that will hold the tasks.

            //checking that 'toTransfer' column has enough space for the new tasks that are going to be added.
            if (toTransferLimit!=-1 && toTransferLimit<sumOfTasks)
            {
                log.Info("Cannot delete column -> not enough room for the tasks to got to from Column:" + columnOrdinal + "to Column: " + toTransferOrdinal);
                throw new Exception("Cannot delete the column because there is not enough room to move the tasks to column number: " + toTransferOrdinal);
            }
            for (int i = columnOrdinal; i<maxId; i++)
            {
                columns[i] = columns[i + 1];
            }

            columns.Remove(maxId);//Removing the leftover column.

            foreach(Task t in deletedColumn.GetTasks().Values)
            {
                toTransfer.AddTask(t);
            }
            deletedColumn.DeleteColumn();//Deleting from the data base.
            UpdateColumnsColumnId();//Updating the new Columns columnId fields in the data base.
            log.Info("Column has been removed succesfully");


        }
       
        /// <summary>
        /// Method recieves an index argument, checks if it is valid, and return the column of that index.
        /// </summary>
        /// <param name="index"></param>
        /// <returns></returns>
        internal Column GetColumn(int ColumnId) {
            if (!columns.ContainsKey(ColumnId))
            {
                log.Error("The column number requested was out of bounds (number: " + ColumnId);
                throw new System.ArgumentException("the index is bigger then 2 or smaller then 0");
            }
            return columns[ColumnId];
        }
       
        /// <summary>
        ///increasing a column spot in the order by 1
        /// getting the successor of the column in the order to its place in exchange
        /// update the new order of columns in the columns table in the data
        /// </summary>
        /// <param name="cOrdinal">the column number we want to move</param>
        public virtual void MoveColumnRight(int cOrdinal) {
        {
            int maxId = GetMaxColumnId();
            if (cOrdinal < 0 || cOrdinal > maxId)
            {
                log.Warn("out of bounds column ordinal output");
                throw new Exception("The Column Ordinal is out of bounds");
            }
            if (cOrdinal == maxId)
            {
                log.Warn("can not move the last columns to the right");
                throw new Exception("Cannot move the last column right");
            }
            Column temp = columns[cOrdinal];
            columns[cOrdinal] = columns[cOrdinal + 1];
            columns[cOrdinal + 1] = temp;
            UpdateColumnsColumnId();
            log.Debug("moved the" + cOrdinal + "column to the right");

        }
    }
       
        /// <summary>
        ///decreasing a column spot in the order by 1
        /// getting the predecessor of the column in the order to its place in exchange
        /// update the new order of columns in the columns table in the data
        /// </summary>
        /// <param name="cOrdinal">the column number we want to move</param>
        public virtual void MoveColumnLeft(int cOrdinal)
        {
            int maxId = GetMaxColumnId();
            if (cOrdinal < 0 || cOrdinal > maxId)
            {
                log.Warn("out of bounds column ordinal output");
                throw new Exception("The Column Ordinal is out of bounds");    
            }
            if (cOrdinal == 0)
            {
                log.Warn("can not move the first columns to the left");
                throw new Exception("Cannot move the first column left");
            }
            
            Column temp = columns[cOrdinal];
            columns[cOrdinal] = columns[cOrdinal - 1];
            columns[cOrdinal - 1] = temp;
            UpdateColumnsColumnId();
            log.Debug("moved the" +cOrdinal + "column to the left");

        }
       
        /// <summary>
        /// Throws an error if the name doesnt exist
        /// Method recieves a column name , and returns the column with the same name, if it exists.
        /// </summary>
        /// <param name="name">Column name</param>
        /// <returns> Buessines layer column object</returns>
        internal Column GetColumn(string name)
        {
            foreach(Column c in this.columns.Values)
            {
                if (c.GetName() == name)
                    return c;
          
            }
            log.Error("The name doesn't match any existing column name");
            throw new System.ArgumentException("the name isn't matching any column name");
      
            
        }

        /// <summary>
        /// returns the columnArr field
        /// </summary>
        /// <returns> columnArr field </returns>
        internal Dictionary<int,Column> GetColumns()
        {
            return columns;
        }


        internal int GetMaxColumnId()
        {
            return columns.Count-1;
        }

        /// <summary>
        /// Method calls the AddTask() method of the first column and which adds a new task to that column.
        /// Increasing the MaxIdCounter by one because a new Task was added to the board.
        /// </summary>
        /// <param name="title">new task's title</param>
        /// <param name="description">new task's description</param>
        /// <param name="dueDate">new task's due date</param>
        /// <returns>The id of the new task that was just created</returns>
        internal int AddTask(string title, string description, DateTime dueDate,string emailAs)
        {
            columns[0].AddTask(TaskIdCounter, title, description, dueDate,emailAs);
            TaskIdCounter = TaskIdCounter + 1;
            return TaskIdCounter - 1;
        }

        
        /// <summary>
        /// Method calls the SetLimit() method of the column with the column ordinal sent.
        /// </summary>
        /// <param name="cOrdinal">the ordinal number of the column who's limit we want to change</param>
        /// <param name="limit">The new limit to set for that column</param>
        internal virtual void SetLimit(int cOrdinal,int limit)
        {          
            GetColumn(cOrdinal).SetLimit(limit);
        }

        /// <summary>
        /// Method calls the SetDueDate() method of the column with the column ordinal sent.
        /// Throws an error if the column ordinal is 2, because the last column's tasks's due date cannot be chagned.
        /// </summary>
        /// <param name="cOrdinal">the ordinal number of the column that contains the required task </param>
        /// <param name="taskId">The required task's ID</param>
        /// <param name="dueDate">the due date to set to the required task</param>
        internal void SetDueDate(string ChangerEmail, int cOrdinal, int taskId, DateTime dueDate)
        {
            CheckIfCanChangeInColumn(cOrdinal);
            GetColumn(cOrdinal).SetDueDate(ChangerEmail, taskId, dueDate);
            

        }

        /// <summary>
        /// Method calls the SetDescription() method of the column with the column ordinal sent.
        /// Throws an error if the column ordinal is 2, because the last column's tasks's description cannot be chagned.
        /// </summary>
        /// <param name="cOrdinal">the ordinal number of the column that contains the required task </param>
        /// <param name="taskId">The required task's ID</param>
        /// <param name="description">the description to set to the required task</param>
        internal void SetDescription(string ChangerEmail, int cOrdinal,int taskId,string description)
        {
            CheckIfCanChangeInColumn(cOrdinal);
            GetColumn(cOrdinal).SetDescription(ChangerEmail, taskId, description);
        }

        /// <summary>
        /// Method calls the SetTitle() method of the column with the column ordinal sent.
        /// Throws an error if the column ordinal is 2, because the last column's tasks's title cannot be chagned.
        /// </summary>
        /// <param name="cOrdinal">the ordinal number of the column that contains the required task </param>
        /// <param name="taskId">The required task's ID</param>
        /// <param name="title">the title to set to the required task</param>
        internal void SetTaskTitle(string ChangerEmail, int cOrdinal, int taskId, string title)
        {
            CheckIfCanChangeInColumn(cOrdinal);

            GetColumn(cOrdinal).SetTitle(ChangerEmail, taskId, title);
        }

        /// <summary>
        ///  Advances a task from the cOrdinal sent to the next one, by calling the AdvanceTask() of Column, which gets the next column as a parameter.
        /// </summary>
        /// <param name="cOrdinal">the ordinal number of the column that contains the required task </param>
        /// <param name="taskId">The required task's ID</param>
        internal void AdvanceTask(string emailFrom,int cOrdinal,int taskId)
        {
            CheckIfCanChangeInColumn(cOrdinal);
            if (!columns[cOrdinal].GetTasks().ContainsKey(taskId))
            {
                log.Warn("Cannot advance a task without the right column");
                throw new Exception("The task id is not in the column ");
            }
            GetColumn(cOrdinal).AdvanceTask(emailFrom, taskId, GetColumn(cOrdinal + 1));
        }
        
        /// <summary>
        /// Changes the name of a column in the board.
        /// </summary>
        /// <param name="columnOrdinal">The ordinal number of the column we want to change the name to </param>
        /// <param name="newName">The new name to set as the column name</param>
        internal virtual void ChangeColumnName(int columnOrdinal, string newName)
        {

            if (IsColumnNameExists(newName))
            {
                log.Warn("The column named: " + newName + " cant be added because this name exists");
                throw new Exception("This column name already exists");
            }
            GetColumn(columnOrdinal).ChangeColumnName(newName);
        }

        /// <summary>
        /// Deletes a task that is in the board
        /// </summary>
        /// <param name="ChangerEmail">The user that request the deletion</param>
        /// <param name="columnOrdinal">The ordinal number of the column that contains the email</param>
        /// <param name="taskId">The task's id.</param>
        internal void DeleteTask(string ChangerEmail, int columnOrdinal, int taskId)
        {  //TODO:Cant delete last column tasks?

            GetColumn(columnOrdinal).DeleteTask(ChangerEmail,taskId);
        }
        
        /// <summary>
        /// Returns this Object as a Dal.Board object.
        /// </summary>
        /// <returns>this Board object as Dal.Board object</returns>
        internal override DLboard ToDalObject()
        {
            DLboard DLB = new DLboard(this.id,true);
            return DLB;
        }

        /// <summary>
        /// Returns a new dictionary of column where the key is the column data base id and the value is the column ordinal.
        /// </summary>
        /// <returns></returns>
        private Dictionary<long,long> GetColumnIdColumnDictionary()
        {
            Dictionary<long, long> ColumnsByColumnId = new Dictionary<long, long>();
            foreach(KeyValuePair<int,Column> cPair in columns)
            {
                //<dbKey,ColumnId>
                ColumnsByColumnId.Add(cPair.Value.GetDataBaseID(),cPair.Key);
            }

            return ColumnsByColumnId;
        }
        
        /// <summary>
        ///a helping functuin
        /// after changing the order of columns, update their new id's in the data
        /// </summary>
        /// <param name="c">column controller - activates a function of updating the database</param>
        internal void UpdateColumnsColumnId()
        {
            ColumnController c = new ColumnController();
            c.UpdateColumnsId(GetColumnIdColumnDictionary());
            
        }
    }
}
