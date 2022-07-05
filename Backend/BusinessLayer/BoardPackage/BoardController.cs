using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DlBoardController = IntroSE.Kanban.Backend.DataAccessLayer.BoardController;
using DlColumnController = IntroSE.Kanban.Backend.DataAccessLayer.ColumnController;
using DlTaskController = IntroSE.Kanban.Backend.DataAccessLayer.TaskController;
using DlBoard = IntroSE.Kanban.Backend.DataAccessLayer.Objects.Board;
using DlColumn = IntroSE.Kanban.Backend.DataAccessLayer.Objects.Column;
using DlTask = IntroSE.Kanban.Backend.DataAccessLayer.Objects.Task;
using IntroSE.Kanban.Backend.BusinessLayer.BoardPackage;


namespace IntroSE.Kanban.Backend.BusinessLayer
{
    class BoardController
    {
        private  DlBoardController dlBC;
        private  DlTaskController dlT ;
        private  DlColumnController dlC;
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        private UserController uc;

        /// <summary>
        /// Constructor for a new Board controller that sets
        /// a user controller as a field.
        /// </summary>
        /// <param name="uc"> The user controller.</param>
        internal BoardController(UserController uc)
        {
            this.uc = uc;
           dlBC= new DlBoardController();
           dlT = new DlTaskController();
           dlC = new DlColumnController(); 
        }

        /// <summary>
        /// Method gets the board of the current user
        /// </summary>
        /// <param name="email">the email of the current user</param>
        /// <returns>The Board of the current user.</returns>
        internal Board GetBoard(string email)
        {
            
            return uc.GetBoard(email);
       
        }

        /// <summary>
        /// Deleting all of the persistent data from the data base of the Tasks, the Columns, and the Boards.
        /// </summary>
        internal void DeleteData()
        {
            dlT.DeleteAllData();//Deleting tasks first.
            dlC.DeleteAllData();//Deleting Columns second.
            dlBC.DeleteAllData();//Deleting boards third.
            uc.DeleteData();//Deleting Users last.
        }

        /// <summary>
        /// Loads all of the persistent data of the tasks, the columns, and the boards from the data base.
        /// </summary>
        internal void LoadData()
        {
           
            List<DlBoard> dlBoardsList = dlBC.SelectAllColumns();
            List<DlColumn> dlColumnsList = dlC.SelectAllColumns();
            List<DlTask> dlTasksList = dlT.SelectAllColumns();

            log.Debug("Loaded all of the info of Tasks,Columns and Boards from the data base. Converting to Business Data");
            Dictionary<int, Board> boardsById = new Dictionary<int, Board>();
            foreach(DlBoard dlb in dlBoardsList)
            {
                List<DlColumn> boardColumns = new List<DlColumn>();
                foreach (DlColumn c in dlColumnsList)
                    if (c.boardId == dlb.Id) 
                        boardColumns.Add(c);

                //creating a dictionary and adding to it Business Later Columns.
                Dictionary<int, Column> businessColumnsOfBoard = new Dictionary<int, Column>();
                foreach(DlColumn dlc in boardColumns)
                {
                    List<DlTask> DlTasks = new List<DlTask>();//Creating a new dictionary of tasks for each column.
                    foreach(DlTask dlt in dlTasksList)//adding to a dictionary all of the tasks of a certain column.
                        if(dlt.columnId==dlc.Id)
                            DlTasks.Add(dlt);

                    businessColumnsOfBoard.Add((int)dlc.columnId, new Column(dlc,DlTasks));//Adding a business layer column to the dicitonary.
                    
                }
                //creating the new board and adding it to the dictionary of boards.
                Board bBoard = new BoardHost(dlb, businessColumnsOfBoard);
                boardsById.Add((int)dlb.Id, bBoard);
            }
            //Finished gathering all boards data.

            log.Debug("All of the tasks, columns and boards persistent information has been converted into Business Layer information successfully");

            log.Debug("trying to load the user's persistent data... ");
            uc.LoadData(boardsById);

        }

        /// <summary>
        /// Adding a column to the board of the requested user at the requested ordinal number.
        /// </summary>
        /// <param name="email">the email of the requested user--> Must be logged In</param>
        /// <param name="columnCordinal">The ordinal number which the column will be added at</param>
        /// <param name="name"></param>
        public void AddColumn(string email, int columnCordinal, string name)
        {
            Board board = GetBoard(email);
            board.AddColumn(columnCordinal, name);
        }

        

        /// <summary>
        /// Assigning an existing task to a user.
        /// </summary>
        /// <param name="email">the email of whomever assigns the task</param>
        /// <param name="columnOrdinal">the column ordinal which contains the task</param>
        /// <param name="taskId">the task that we want to chagne</param>
        /// <param name="emailAssignee"> the new email to assign the task to</param>
        internal void AssignTask(string email, int columnOrdinal, int taskId, string emailAssignee)
        {

            
            Board board = GetBoard(email);
            CheckIfBoardMember(emailAssignee, board);
            if (email.ToLower() == emailAssignee.ToLower())
            {
                log.Warn("Cannot change the assignee to the current email assignee");
                throw new Exception("You are already assigned to this task");
            }
            board.AssignTask(email,columnOrdinal, taskId, emailAssignee);
        }

        /// <summary>
        /// Checks if the user's board matches the board in the argument
        /// </summary>
        /// <param name="email">The user that we compare it's board's email</param>
        /// <param name="board">The board to match with the user's board.</param>
        private void CheckIfBoardMember(string email,Board board)
        {
            uc.CheckIfBoardMember(email.ToLower(), board);
        }

        /// <summary>
        /// Removing the column at the requested orindal number.
        /// </summary>
        /// <param name="email"></param>
        /// <param name="coulmnCordianl"></param>
        public void RemoveColumn(string email, int coulmnCordianl)
        {

            Board temp = GetBoard(email);
            
            temp.RemoveColumn(coulmnCordianl);
        }
        /// <summary>
        /// Method creats and adds a new task and adds it to the first column of the current users board 
        /// </summary>
        /// <param name="email">the current user's email</param>
        /// <param name="title">the new task's title</param>
        /// <param name="description">the new task's description</param>
        /// <param name="dueDate">the new task's due date</param>
        /// <returns>the new task's id</returns>
        internal int AddTask(string email, string title, string description, DateTime dueDate)
        {
            
            Board userBoard = GetBoard(email);
            int taskid= userBoard.AddTask(title,description,dueDate,email);
            return taskid;
        }

        /// <summary>
        /// Moving the column at the ordinal number requested on place right
        /// </summary>
        /// <param name="email">the email of the logged in user which the column is in it's board</param>
        /// <param name="columnOrdinal">the ordinal number of the column we want to move</param>
        public void MoveColumnRight(string email, int columnOrdinal)
        {

            Board board = GetBoard(email);
            board.MoveColumnRight(columnOrdinal);
        }

        /// <summary>
        /// Changes a name of a column in the board.
        /// </summary>
        /// <param name="email">the email of the logged in user.</param>
        /// <param name="columnOrdinal">the ordinal number of the column</param>
        /// <param name="newName">The new name to set for the column.</param>
        internal void ChangeColumnName(string email, int columnOrdinal, string newName)
        {

            Board board = GetBoard(email);
            
            board.ChangeColumnName(columnOrdinal, newName);

        }

        /// <summary>
        /// Returns the creator email of a board.
        /// </summary>
        /// <param name="board">The board which we want it's creator email</param>
        /// <returns> Email of the creator of the board.</returns>
        internal string getCreatorEmail(Board board)
        {
            return board.CreatorEmail;
        }

        /// <summary>
        /// Moving the column at the ordinal number requested on place left
        /// </summary>
        /// <param name="email">the email of the logged in user which the column is in it's board</param>
        /// <param name="columnOrdinal">the ordinal number of the column we want to move</param>
        public void MoveColumnLeft(string email, int columnOrdinal) {

            Board board = GetBoard(email);
            
            board.MoveColumnLeft(columnOrdinal);

        }
        /// <summary>
        /// changing the limit of a column
        /// </summary>
        /// <param name="email">the email of the current user</param>
        /// <param name="cOrdinal">the number of the column who's limit is being changed</param>
        /// <param name="limit">the new limit to set</param>
        internal void LimitColumnTasks(string email, int cOrdinal, int limit)
        {

            Board board = GetBoard(email);
            board.SetLimit(cOrdinal,limit);
            
        }
        /// <summary>
        /// Calls  the user's Board SetDueDate(),saves the information changed afterwords.
        /// </summary>
        /// <param name="ChangerEmail">the email of the current user</param>
        /// <param name="cOrdinal">the ordinal number of the column which contains the required task</param>
        /// <param name="taskId">the task's id of the task we want to change</param>
        /// <param name="dueDate">the new due date to set</param>
        internal void UpdateTaskDueDate(string ChangerEmail ,int cOrdinal, int taskId, DateTime dueDate)
        {
            GetBoard(ChangerEmail).SetDueDate(ChangerEmail,cOrdinal,taskId,dueDate);
            
        }

        /// <summary>
        /// Calls  the user's Board SetDescription(),saves the information changed afterwords.
        /// </summary>
        /// <param name="ChangerEmail">the current user's email</param>
        /// <param name="cOrdinal">the orindal number of the column which contains the reqired task</param>
        /// <param name="id">the required tas's id</param>
        /// <param name="desctiption">the new description to set </param>
        internal void UpdateTaskDescription(string ChangerEmail, int cOrdinal, int id, string desctiption)
        {
             GetBoard(ChangerEmail).SetDescription(ChangerEmail,cOrdinal,id,desctiption);
           
        }

        /// <summary>
        /// Calls the user's  Board SetTaskTitle(),saves the information changed afterwords.
        /// </summary>
        /// <param name="ChangerEmail">the current user's email</param>
        /// <param name="cOrdinal">the ordinal number of the column which contains the reuired task</param>
        /// <param name="id">the required task's id</param>
        /// <param name="title">the new title to set</param>
        internal void UpdateTaskTitle(string ChangerEmail, int cOrdinal, int id, string title)
        {
            GetBoard(ChangerEmail).SetTaskTitle(ChangerEmail, cOrdinal,id,title);
            
        }

        /// <summary>
        /// Deletes a task in the board.
        /// </summary>
        /// <param name="ChangerEmail">The user that wants to delete email.</param>
        /// <param name="columnOrdinal">The ordinal number of the column which has the task.</param>
        /// <param name="taskId">The task to delete's id.</param>
        internal void DeleteTask(string ChangerEmail, int columnOrdinal, int taskId)
        {
            Board board = GetBoard(ChangerEmail);
            board.DeleteTask(ChangerEmail, columnOrdinal, taskId);
        }

        /// <summary>
        /// Calls  the user's Board GetColumn(),with a column ordinal number, which returns that column.
        /// </summary>
        /// <param name="email">the current user's email</param>
        /// <param name="cOrdinal">the ordinal number of the task that we want to get</param>
        /// <returns>Returns the Column object according to the cOrdinal sent.</returns>
        internal Column GetColumn(string email, int cOrdinal)
        {

            return GetBoard(email).GetColumn(cOrdinal);
        }

        /// <summary>
        /// Calls Board GetColumn(), with the column name as the identifier, which returns that colum
        /// </summary>
        /// <param name="email">the current user's email</param>
        /// <param name="name">the name of the required column</param>
        /// <returns>Returns the Column object according to the name sent.</returns>
        internal Column GetColumn(string email, string name)
        {
            Board userBoard = GetBoard(email);
            Column c = null;
            foreach(Column x in userBoard.GetColumns().Values)
            {
                if (x.GetName() == name)
                    c = x;
            }

            if (c == null)
            {
                log.Warn("No column with the name :" + name + " exists");
                throw new Exception("Column name is not valid");
            }
            return c;

        }

        /// <summary>
        /// Calls the user's Board AdvanceTask(), which advances the task with the Id sent one column forwards.
        /// </summary>
        /// <param name="email">the current user's email</param>
        /// <param name="cOrdinal">the orindal number of the column which contains the reqired task</param>
        /// <param name="taskId">the required tas's id</param>
        internal void AdvanceTask(string email,int cOrdinal,int taskId)
        {
            Board userBoard = GetBoard(email);
            userBoard.AdvanceTask(email,cOrdinal, taskId);
            
        }

       
        
    }
}
