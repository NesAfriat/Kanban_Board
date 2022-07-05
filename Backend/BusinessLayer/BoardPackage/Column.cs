using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using DLColumn = IntroSE.Kanban.Backend.DataAccessLayer.Objects.Column;
using DLTask = IntroSE.Kanban.Backend.DataAccessLayer.Objects.Task;
[assembly: InternalsVisibleTo("UnitTestKanban")]

namespace IntroSE.Kanban.Backend.BusinessLayer
{
    class Column:PersistedObject<DLColumn>
    {
       
        
        private Dictionary<int,Task> tasks;
        private string name;
        private const int nameLimit = 15;
        private int limit;
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        /// <summary>
        /// Constructor for a new column
        /// </summary>
        /// <param name="name">The new column's name</param>
        /// <param name="limit">the new column's limit</param>
        /// <param name="colId">the new column's id</param>
        /// <param name="boardId">the Board id which the column belongs to</param>
        internal Column(string name, int limit, long colId, long boardId)
        {
            this.tasks = new Dictionary<int, Task>();
            NameCheck(name);
            if (!IsValidLimit(limit))
            {
                log.Warn("Bad limit input to a new Column Object!");
                throw new Exception("Illegal input for limit");
            }
             DLColumn dlc = new DLColumn(colId, name, limit, boardId);
            dlc.Insert();
            
            this.name = name;
            this.id = dlc.Id;
            this.limit = limit;
        }

        /// <summary>
        /// Assignes a new email to a task's email assignee.
        /// </summary>
        /// <param name="emailFrom">The email of the user that requests the change</param>
        /// <param name="taskId">The task's id</param>
        /// <param name="emailAssignee">The new email to assign.</param>
        internal void AssignTask(string emailFrom ,int taskId, string emailAssignee)
        {
            Task task=GetTask(taskId);
            task.AssignTask(emailFrom, emailAssignee);
            log.Debug("Changed task assignee");
        }

        /// <summary>
        /// Checking if the limit that was entered is valid.
        /// </summary>
        /// <param name="newLimit">the limit</param>
        /// <returns> true if the limit is valid, false elseway</returns>
        internal bool IsValidLimit(int newLimit)
        {
            return newLimit >= 0;
        }
        
        /// <summary>
        ///a constructor for the LoadData fucntion
        ///getting a DTO columns and creates a business column
        /// </summary>
        /// <param name="dlcolumn">the DTO object that holds this column's data</param>
       /// <param name="dltasks">the DTO tasks list - in order to add the relavent tasks that hold this column's key</param>
        internal Column(DLColumn dlcolumn,List<DLTask> dlTasks)
        {
            tasks = new Dictionary<int, Task>();
            this.id = dlcolumn.Id;
            this.name = dlcolumn.name;
            this.limit =(int)dlcolumn.limit;
            foreach(DLTask dlt in dlTasks)
            {
                Task bT = new Task(dlt);
                tasks.Add((int)dlt.taskId, bT);
            }
            
        }

        /// <summary>
        /// Creates a new Task and calling the AddTask(Task t) method which adds the task to the current column
        /// </summary>
        /// <param name="task"></param>
        internal Task AddTask(int taskId,string title, string description, DateTime dueTo,string emailAs)
        {
            if (IfIsFull())
            {
                throw new Exception("The Column has reached it's limit of tasks");
            }
            Task t = new Task(taskId, DateTime.Now, title,description ,dueTo,this.id,emailAs);
            AddTask(t);
            return t;
        }
       
        //Returns the tasks collection of the column
        internal Dictionary<int,Task> GetTasks()
        {
            return tasks;
        }

        //Checking if the column has place left to add a new task.
        internal bool IfIsFull()
        {
            return tasks.Count >= limit;
        }
        
        /// <summary>
        /// Setting this column's limit
        /// throwing exception if the limit is negative or there are more tasks than the required new limit.
        /// limit -1 is okay and indicates of no limit.
        /// </summary>
        /// <param name="newLimit">the new limit to set for the current column</param>
        internal void SetLimit(int newLimit)
        {
            if (!IsValidLimit(newLimit)) {
                log.Warn("The limit cannot be negative");
                throw new System.ArgumentException("A column's limit cannot be negative!");
            }
            if (GetNumOfTasks() > newLimit)
            {
                log.Warn("The limit cannot be lower than the number of current tasks in the column");
                throw new System.ArgumentException("The column's limit cannot be set to be less of the number of current tasks in the column");
            }
           
            this.limit = newLimit;
            ToDalObject().limit = newLimit;
        }

        //getter for the name.
        internal string GetName()
        {
            return name;
        }

        /// <summary>
        /// Checks if the new name is valid
        /// Changes the current column's name.
        /// Also chagnes it in the data base.
        /// </summary>
        /// <param name="newName">The new name</param>
        internal void ChangeColumnName(string newName)
        {

            NameCheck(newName);
            this.name = newName;
            ToDalObject().name = name;
            log.Info("Name was changed succefuly!");
        }
        

        /// <summary>
        /// a helping method
        /// checks the the name for a columns is in the proper length and throws exceptions accordingly.
        /// </summary>
        /// <param name="n">a name for column to check</param>
        /// Internal for testing.
        internal void NameCheck(string n)
        {

            if (String.IsNullOrWhiteSpace(n) || n.Length == 0)
            {
                string err = "Column name can't be empty!";
                log.Warn(err);
                throw new Exception(err);
            }
            if (n.Length > nameLimit)
            {
                string err = "Column name cannot be longer than " + nameLimit + " characters!";
                log.Warn(err);
                throw new Exception(err);
            }
        }
        
        internal int GetLimit()
        {
            return limit;
        }

        //Returns the task with the taskId requested if it is in this column.
        internal Task GetTask(int taskId)
        {
            if (!tasks.ContainsKey(taskId))
            {
                log.Error("The task id you are trying to get doesn't exist");
                throw new System.ArgumentException("The task's id doesn't correspond with any exsiting task");
            }
            return tasks[taskId];
        }

        //Returns the number of current tasks.
        internal int GetNumOfTasks() { return tasks.Count; }


        /// <summary>
        /// Sets the task's new due date
        /// </summary>
        /// <param name="TaskId">the ruqired task's id</param>
        /// <param name="dueDate">the new due date to set for the required task</param>
        internal void SetDueDate(string ChagnerEmail ,int TaskId,DateTime dueDate)
        {
            
            GetTask(TaskId).SetDueDate(ChagnerEmail, dueDate);
        }

        /// <summary>
        /// setting  a new description to the required task with the taskId.
        /// </summary>
        /// <param name="TaskId">the ruqired task's id</param>
        /// <param name="description"> the new description to set for the required description</param>
        internal void SetDescription(string ChangerEmail, int taskId,string description)
        {
           
            GetTask(taskId).SetDescription(ChangerEmail, description);
        }

        /// <summary>
        /// Sets a new title for the task with the id == 'taskId'
        /// </summary>
        /// <param name="TaskId">the ruqired task's id</param>
        /// <param name="title"> the new title to set for the required task</param>
        internal void SetTitle(string ChangerEmail, int taskId,string title)
        {
            
            GetTask(taskId).SetTitle(ChangerEmail, title);
        }
        
        /// <summary>
        /// Deletes a task from the current column.
        /// checks if the user that requested the delete is the task's assignee.
        /// </summary>
        /// <param name="ChangerEmail">The email of the user that requesets the deletion</param>
        /// <param name="taskId">The task's id to delete</param>
        internal void DeleteTask(string ChangerEmail,int taskId)
        {
            Task taskToRemove = GetTask(taskId);
            taskToRemove.CheckIfAssignee(ChangerEmail);
            taskToRemove.ToDalObject().Delete();
            tasks.Remove(taskId);
            log.Info("Task was removed successfully");
        }
       
        /// <summary>
        /// Advancing a task from this column to the next column
        /// (deleting it from the preivous column and adding to the next)
        /// </summary>
        /// <param name="taskId">The task which we want to advance</param>
        /// <param name="toAdvance"> the column which we advance the task too</param>
        internal void AdvanceTask(string emailFrom,int taskId,Column toAdvance)
        {
                       
            Task adv = GetTask(taskId);
            adv.CheckIfAssignee(emailFrom);//Throws an error if the 'emailFrom' is not the assignee of the task.
            toAdvance.AddTask(adv);
            tasks.Remove(taskId);

        }

        /// <summary>
        /// a private fucntion that gets a task and if it's id doesnt exist and if
        /// there are no exception from the limit add the task to the collection
        /// </summary>
        /// <param name="task">the new Task object to add to this column</param>
        public void AddTask(Task task)
        {
            if (IfIsFull())
            {
                    log.Warn("Cannot add the task to this column because it reached it's tasks limit");
                    throw new Exception("Cannot add a task because this column has reached it's tasks limit!");
            }
            if (task == null)
            {
                log.Error("Cannot add null task!");
                throw new System.ArgumentNullException("task argument is null");
            }
            int taskId = task.GetTaskId();
            if (tasks.ContainsKey(taskId))
            {
                log.Error("This task id already exists in the current column (name: "+name);
                throw new Exception("The task id you are trying to add already exists");
            }
            task.ToDalObject().columnId = GetDataBaseID();//Updating the column id in the data base.
            tasks.Add(taskId, task);

        }
        /// <summary>
        /// return the key the perssistance class hold of this oboject's spot in the SQL table
        /// </summary>
        /// <param name="limit">the new limit to set for the current column</param>
        internal long GetDataBaseID()
        {
            return this.id;
        }

        //Deletes the column from the data base.
        internal void DeleteColumn()
        {
            ToDalObject().Delete();
        }
      
        /// <summary>
        /// conver the task into Dal Task's type and adding the dal task to the daltasks collection
        /// </summary>
        /// <returns></returns>
        internal override DLColumn ToDalObject()
        {
            
            DLColumn DLC = new DLColumn(this.id);

            return DLC;
        }
    }
}
