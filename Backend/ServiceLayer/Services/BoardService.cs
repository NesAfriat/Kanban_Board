using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using IntroSE.Kanban.Backend.BusinessLayer;
using bsUser = IntroSE.Kanban.Backend.BusinessLayer.UserPackage.User;
using bsTask = IntroSE.Kanban.Backend.BusinessLayer.Task;
using bsColumn = IntroSE.Kanban.Backend.BusinessLayer.Column;
using bsBoard = IntroSE.Kanban.Backend.BusinessLayer.Board;

namespace IntroSE.Kanban.Backend.ServiceLayer
{
    class BoardService
    {
       
        
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        private BoardController bc;

        internal BoardService(UserController uc)
        {            
            bc = new BoardController(uc);
        }
        /// <summary>
        /// Deleting all persistent data from RAM and from the Data base.
        /// </summary>
        /// <returns></returns>
        internal Response DeleteData()
        {
            try
            {
                bc.DeleteData();
                log.Debug("All data was deleted");
                return new Response(null);
            }
            catch(Exception e)
            {
                log.Error("Failed to delete all data");
                return new Response(e.Message);
            }
        }

        /// <summary>
        /// assign a task to a given email - for permissions 
        /// </summary>
        /// <param name="email">The email of the currnt task owner</param>
        /// <param name="columnOrdinal">The ordinal of the column the task is at</param>
        /// <param name="taskId">the task's Id</param>
        /// /// <param name="emailAssignee">The email of the user to give the ownership to</param>
        /// <returns>A new response with an error if an error occurd, and couldnt make the given email the ownership of the task</returns>
        internal Response AssignTask(string email, int columnOrdinal, int taskId, string emailAssignee)
        {
            try
            {
                bc.AssignTask(email, columnOrdinal, taskId, emailAssignee);
                return new Response();
            }
            catch(Exception e)
            {
                log.Debug("Couldn't change the email assignee");
                return new Response(e.Message);
            }
        }
            /// <summary>
            /// Loading the persistent data data
            /// </summary>
            /// <returns>Response object which contains an error if an excetion was thrown, or null else</returns>
            internal Response LoadData()
        {
            try
            {
                bc.LoadData();
                log.Debug("Data loaded successfuly");
                return new Response(null);
            }
            catch(Exception e)
            {
                log.Error("Failed to load persistent data!");
                return new Response(e.Message);
            }
        }
        /// <summary>
        /// Adding a column at the requested Ordinal number.
        /// </summary>
        /// <param name="email">The logged in user's email</param>
        /// <param name="columnOrdinal">The ordinal place which the column will be put at</param>
        /// <param name="name">Column name</param>
        /// <returns>A new response with an error if an error occurd, and the column object if the column was added successfully.</returns>
        internal Response<Column> AddColumn(string email, int columnOrdinal, string name)
        {
            try
            {
                bc.AddColumn(email, columnOrdinal, name);
                Response<Column> rC = GetColumn(email, columnOrdinal);
                if (rC.ErrorOccured)
                    throw new Exception(rC.ErrorMessage);

                log.Info("Column was added successfully");
                return rC;
            }
            catch(Exception e)
            {
                log.Debug("Couldn't add column");
                return new Response<Column>(e.Message);
            }
        }
        /// <summary>
        /// Removing the column at the colum Ordinal.
        /// </summary>
        /// <param name="email">email of the logged in user</param>
        /// <param name="coulmnCoridnal">The ordinal number of the column which we want to delete</param>
        /// <returns>Response object which contains an error if an excetion was thrown, or null else</returns>
        public Response RemoveColumn(string email, int coulmnCoridnal)
        {
             try
            {
                bc.RemoveColumn(email, coulmnCoridnal);
                log.Info("Column has been removed successfuly");
                return new Response<Column>(null);
            }
            catch (Exception e)
            {
                log.Info("Coludn't remove column");
                return new Response<Column>(e.Message);
            }
        }

        /// <summary>
        /// Moving the column at the column ordinal one place right.
        /// </summary>
        /// <param name="email">email of the logged in user</param>
        /// <param name="coulmnCoridnal">The ordinal number of the column which we want to move right</param>
        /// <returns>A new response with an error if an error occurd, and the column object if the column was moved successfully.</returns>
        public Response<Column> MoveColumnRight(string email, int columnOrdinal)
        {
            try
            {
                Response<Column> rC = GetColumn(email, columnOrdinal);
                bc.MoveColumnRight(email, columnOrdinal);
                if (rC.ErrorOccured)
                    throw new Exception(rC.ErrorMessage);
                log.Info("Column has been moved to the right");
                return new Response<Column>(rC.Value);
            }
            catch(Exception e)
            {
                log.Info("Couldn't remove column to the right");
                return new Response<Column>(e.Message);
            }
        }
        /// <summary>
        /// Moving the column at the column ordinal one place left.
        /// </summary>
        /// <param name="email">email of the logged in user</param>
        /// <param name="coulmnCoridnal">The ordinal number of the column which we want to move left</param>
        /// <returns>A new response with an error if an error occurd, and the column object if the column was moved successfully.</returns>
        public Response<Column> MoveColumnLeft(string email, int columnOrdinal)
        {
            try
            {
                Response<Column> rC = GetColumn(email, columnOrdinal);
                bc.MoveColumnLeft(email, columnOrdinal);
                if (rC.ErrorOccured)
                    throw new Exception(rC.ErrorMessage);
                log.Info("Column has been moved to the left");
                return new Response<Column>(rC.Value);
            }
            catch (Exception e)
            {
                log.Info("Couldn't remove column to the left");

                return new Response<Column>(e.Message);
            }

        }
        /// <summary>
        /// Chane an existing columnn's name 
        /// </summary>
        /// <param name="email">The email of user who is logged in</param>
        /// <param name="columnOrdinal">The ordinal of the column to change</param>
        /// <param name="newName">A new name for the column</param>
        /// <returns>A new response with an error if an error occurd, and the new name give for the column illegal</returns>
        internal Response ChangeColumnName(string email, int columnOrdinal, string newName)
        {
            try
            {
                bc.ChangeColumnName(email, columnOrdinal, newName);
                return new Response();
            }
            catch(Exception e)
            {
                log.Info("Couldn't change name");
                return new Response(e.Message);
            }
        }

        /// <summary>
        /// Calls the BoardController limit function, which changes the column number 'cOrdinal' limit in the current user's board.
        /// </summary>
        /// <param name="email">the email of the current user</param>
        /// <param name="cOrdinal">the ordinal number of the required column</param>
        /// <param name="limit">the new limit for the column</param>
        /// <returns>Response object which contains an error if an excetion was thrown, or null else </returns>
        internal Response LimitColumnTasks(string email, int cOrdinal, int limit)
        {
            try
            {
                bc.LimitColumnTasks(email, cOrdinal, limit);
                log.Info("Column number " + cOrdinal + " limit was changed to - " + limit);
                return new Response(null);
            }
            catch(Exception e)
            {
                log.Debug("Couldn't change the column number: " + cOrdinal + "'s limit");
                return new Response(e.Message);
            }
        }

        /// <summary>
        /// calls the BoardController AddTask() method.
        /// Adding a task to the current user's board.
        /// Creates a Service Layer task with identical information as for the new task that was added.
        /// </summary>
        /// <param name="email">the email of the current user</param>
        /// <param name="title">the title of the task</param>
        /// <param name="description">the description of the new task</param>
        /// <param name="dueDate">the due date of the new task</param>
        /// <returns>Response object which conatins an error message if an error occurd, or the new task that was added</returns>
        internal Response<Task> AddTask(string email, string title, string description, DateTime dueDate)
        {
            try
            {
                int taskId=bc.AddTask(email, title, description, dueDate);
                Task task = new Task(taskId, DateTime.Now, dueDate, title, description,email);
                Response<Task> response = new Response<Task>(task);
                log.Info("A new task was added successfuly");
                return response;
            }
            catch(Exception e)
            {
                log.Info("Couldn't add the new task to the board");
                return new Response<Task>(e.Message);

            }

        }

        /// <summary>
        /// Calling the BoardController UpdateTaskDueDate() method.
        /// Updating a task's due date.
        /// </summary>
        /// <param name="email">the email of the current user</param>
        /// <param name="cOrdinal">the ordinal number of the column that contains the task with the task id</param>
        /// <param name="taskId">the Id of the task that is being edited</param>
        /// <param name="dueDate">the new due date to set</param>
        /// <returns>Respone object which contains an error message if an error occurd , or null else</returns>
        internal Response UpdateTaskDueDate(string email, int cOrdinal, int taskId, DateTime dueDate) {
            try
            {
                bc.UpdateTaskDueDate(email, cOrdinal, taskId, dueDate);
                log.Info("The task's due date was changed successfuly");
                return new Response(null);
            }
            catch (Exception e)
            {
                log.Info("Couldn't change the due date of the task");
                return new Response(e.Message);

            }
        }

        /// <summary>
        /// Calling the BoardController UpdateTaskDescription() method.
        /// Updating a task's description.
        /// </summary>
        /// <param name="email">the email of the current user</param>
        /// <param name="cOrdinal">the ordinal number of the column that contains the task with the task id</param>
        /// <param name="taskId">the Id of the task that is being edited</param>
        /// <param name="description">the new description to set</param>
        /// <returns>Respone object which contains an error message if an error occurd , or null else</returns>
        internal Response UpdateTaskDescription(string email, int cOrdinal, int taskId, string description) {
            try
            {
                bc.UpdateTaskDescription(email, cOrdinal, taskId, description);
                log.Info("The task's description was changed successfully");
                return new Response(null);
            }
            catch (Exception e)
            {
                log.Info("Couldn't change the task's description");
                return new Response(e.Message);

            }
        }

        /// <summary>
        /// Calling the BoardController UpdateTaskTitle() method.
        /// Updating a task's title.
        /// </summary>
        /// <param name="email">the email of the current user</param>
        /// <param name="cOrdinal">the ordinal number of the column that contains the task with the task id</param>
        /// <param name="taskId">the Id of the task that is being edited</param>
        /// <param name="title">the new title to set</param>
        /// <returns>Respone object which contains an error message if an error occurd , or null else</returns>
        internal Response UpdateTaskTitle(string email, int cOrdinal, int taskId, string title)
        {
            try
            {
                bc.UpdateTaskTitle(email, cOrdinal, taskId, title);
                log.Info("The task's title was changed successfully");
                return new Response(null);
            }
            catch (Exception e)
            {
                log.Info("Couldn't change the task's title");
                return new Response(e.Message);

            }
        }


        /// <summary>
        /// Calling the BoardController AdvanceTask() method.
        /// Advancing a task to the next column.
        /// </summary>
        /// <param name="email">the email of the current user</param>
        /// <param name="cOrdinal">the ordinal number of the column that contains the task with the task id</param>
        /// <param name="taskId">the Id of the task that is being edited</param>
        /// <returns>Respone object which contains an error message if an error occurd , or null else</returns>
        internal Response AdvanceTask(string email, int cOrdinal, int taskId)
        {
            try
            {
                bc.AdvanceTask(email, cOrdinal, taskId);
                log.Info("The task was advanced from column number "+cOrdinal+" to column number "+(cOrdinal+1));
                return new Response(null);
            }
            catch (Exception e)
            {
                log.Info("Couldn't advance the task to column number: " + (cOrdinal + 1));
                return new Response(e.Message);

            }
        }
        /// <summary>
        /// Delete an existing task from the board
        /// </summary>
        /// <param name="email">The email of the current logged in user</param>
        /// <param name="columnOrdinal">The ordinal of the column the task is at</param>
        /// <param name="taskId">the task's Id</param>
        /// <returns>A new response with an error if an error occurd, and couldnt delete a task because bad input or lack of access</returns>
        internal Response DeleteTask(string email, int columnOrdinal, int taskId)
        {
            try
            {
                bc.DeleteTask(email, columnOrdinal, taskId);
                return new Response();
            }
            catch(Exception e)
            {
                log.Info("Couldn't delete the reqiured task");
                return new Response(e.Message);
            }
        }
        /// <summary>
        /// returns a Service Layer Column object that contains the data of the column number 'columnOrdinal'
        /// </summary>
        /// <param name="email">the email of the current user</param>
        /// <param name="columnOrdinal">the column's number</param>
        /// <returns>Response object that contains an error message if an error occurd, or the Column object else</returns>
        internal Response<Column> GetColumn(string email, int columnOrdinal)
        {
            try
            {
                bsColumn ColumnBusinessLayer = bc.GetColumn(email,columnOrdinal);
                Response<IReadOnlyCollection<Task>> taskResponse = GetServiceTasksCollection(ColumnBusinessLayer.GetTasks());
                if (taskResponse.ErrorOccured)
                    throw new Exception(taskResponse.ErrorMessage);
                IReadOnlyCollection<Task> readOnlyServiceTasks = taskResponse.Value;
                
                Column column = new Column(readOnlyServiceTasks,ColumnBusinessLayer.GetName(),ColumnBusinessLayer.GetLimit());
                log.Debug("Service Type Column created successfuly");
                return new Response<Column>(column);

            }
            catch (Exception e)
            {
                log.Debug("Couldnt return the requested column\n Error: "+e.Message);
                return new Response<Column>(e.Message);

            }
        }

        /// <summary>
        /// returns a Service Layer Column object that contains the data of the column with the name 'columnName'
        /// </summary>
        /// <param name="email">the email of the current user</param>
        /// <param name="columnName">the column's name</param>
        /// <returns>Response object that contains an error message if an error occurd, or the Column object else</returns>

        internal Response<Column> GetColumn(string email, string columnName) {
            try
            {
                bsColumn c = bc.GetColumn(email, columnName);
                Response<IReadOnlyCollection<Task>> taskResponse = GetServiceTasksCollection(c.GetTasks());
                if (taskResponse.ErrorOccured)
                    throw new Exception(taskResponse.ErrorMessage);
                IReadOnlyCollection<Task> TaskCol = taskResponse.Value;
                Column toReturn = new Column(TaskCol, c.GetName(), c.GetLimit());
                log.Debug("Service Type Column created successfuly");

                return new Response<Column>(toReturn);
            }
            catch(Exception e)
            {
                log.Debug("Couldnt return the requested column\n Error: " + e.Message);
                return new Response<Column>(e.Message);

            }
        }

        /// <summary>
        /// Method creates a Service Layer Board object and transffers the relevent data from the user's board to the 
        /// Service Layer board
        /// </summary>
        /// <param name="email"> the email of the current user</param>
        /// <returns>Response object that contains an error message if an error occurd, or the Service Layer
        /// Board object with the current user's board data otherwise</returns>
        internal Response<Board> GetBoard(string email) {
            try
            {
                bsBoard bsb = bc.GetBoard(email);
                int counter = 0;
                string[] colNamesArr = new string[bsb.GetMaxColumnId()+1];
                foreach(KeyValuePair<int,BusinessLayer.Column> cPair in bsb.GetColumns().OrderBy(p=>p.Key))
                {
                    colNamesArr[counter] = cPair.Value.GetName();
                    counter++;
                }
                IReadOnlyCollection<string> colNames = colNamesArr;
                string creatorEmail = getCreatorEmail(bsb).ToLower();
                Board serviceBoard = new Board(colNames,creatorEmail);
                log.Debug("Service Layer Board was successfuly created");
                return new Response<Board>(serviceBoard);
            }
            catch (Exception e)
            {
                log.Debug("Couldn't create Service Layer board object");
                return new Response<Board>(e.Message);

            }
        }
        /// <summary>
        /// Get the email of the user who created the board in his registration
        /// </summary>
        /// <param name="b">An input board</param>
        /// <returns>A new response with an error if an error occurd, if the function process failed</returns>
        private string getCreatorEmail(bsBoard b)
        {
            return bc.getCreatorEmail(b);
        }
        /// <summary>
        /// Method gets a Dictionary of a BusinessLayer Task type and returns a IReadOnlyCollection of the Service Layer 
        /// Task type with all of the relevance data from the dictionary recieved as an argument.
        /// </summary>
        /// <param name="bsTasks">Business Layer Tasks dictionary</param>
        /// <returns>IReadOnlyCollection of type ServiceLayer Task with the same data as in the dictionary recieved</returns>
        private Response<IReadOnlyCollection<Task>> GetServiceTasksCollection(Dictionary<int,bsTask> bsTasks)
        {
            try
            {
                int count = 0;
                Task[] serviceTasks = new Task[bsTasks.Count];
                foreach (KeyValuePair<int, bsTask> task in bsTasks.OrderBy(p=>p.Key))
                {
                    bsTask currBsTask = task.Value;
                    Task serviceT = new Task(currBsTask.GetTaskId(), currBsTask.GetCraetionTime(), currBsTask.GetDueDate(), currBsTask.GetTitle(), currBsTask.GetDescription(),currBsTask.GetEmailAssignee());
                    serviceTasks[count] = serviceT;
                    count++;
                }

                IReadOnlyCollection<Task> readOnlyServiceTasks = serviceTasks;

                return new Response<IReadOnlyCollection<Task>>( readOnlyServiceTasks);
            }
            catch(Exception e)
            {
                return new Response<IReadOnlyCollection<Task>>(e.Message);
            }
        }
    }
}
