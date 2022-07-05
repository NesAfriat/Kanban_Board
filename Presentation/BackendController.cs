using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using IntroSE.Kanban.Backend.ServiceLayer;
using Presentation.Model;
using serviceTask = IntroSE.Kanban.Backend.ServiceLayer.Task;


namespace Presentation
{
    public class BackendController
    {
        public IService Service { get; private set; }

        //Cunstructor for a backend controller with an existing service.
        public BackendController(IService service)
        {
            this.Service = service;
        }

        //creates a new service and loads the data.
        public BackendController()
        {
            this.Service = new Service();
            Service.LoadData();
        }

        //Logs out from service.
        public void Logout(string email)
        {
            Response res = Service.Logout(email);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
        }

        //Logs in and returns a new User model with the Service user information.
        public UserModel Login(string email, string password)
        {
            Response<User> user = Service.Login(email, password);
            if (user.ErrorOccured)
            {
                throw new Exception(user.ErrorMessage);
            }
            string userMail = user.Value.Email;
            string nick = user.Value.Nickname;
            return new UserModel(this,userMail ,nick ,GetBoard(userMail));
        }

        /// <summary>
        /// Registers a new user with a new board.
        /// </summary>
        /// <param name="username">new username </param>
        /// <param name="password">new password</param>
        /// <param name="nickname">new nickname </param>
        public void Register(string username,string password,string nickname)
        {
            Response res = Service.Register(username, password, nickname);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
            
        }


        /// <summary>
        /// Registers a new user with an existing board.
        /// </summary>
        /// <param name="username">new username </param>
        /// <param name="password">new password</param>
        /// <param name="nickname">new nickname </param>
        public void Register(string username,string password,string nickname,string hostEmail)
        {
            Response res = Service.Register(username, password, nickname,hostEmail);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
        }

        /// <summary>
        /// Advances a task from the service.
        /// </summary>
        /// <param name="email">the logged in user email</param>
        /// <param name="columnOrd">the column ordinal of the task</param>
        /// <param name="taskId">the task's id</param>
        public void AdvanceTask(string email,int columnOrd,int taskId)
        {
            Response res = Service.AdvanceTask(email, columnOrd, taskId);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
        }


        /// <summary>
        /// Deletes a task from the service layer.
        /// </summary>
        /// <param name="email">the logged in user email</param>
        /// <param name="columnOrd">the column ordinal of the task</param>
        /// <param name="taskId">the task's id</param>
        public void DeleteTask(string email,int columnOrd,int taskId)
        {
            Response res = Service.DeleteTask(email, columnOrd, taskId);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
        }
            

        /// <summary>
        /// Updates an existing task's title.
        /// </summary>
        /// <param name="title">the new title</param>
        /// <param name="email">the logged in user email</param>
        /// <param name="columnOrd">the column ordinal of the task</param>
        /// <param name="taskId">the task's id</param>
        public void UpdateTaskTitle(string email,int columnOrd,int taskId,string title)
        {
            Response res = Service.UpdateTaskTitle(email, columnOrd, taskId, title);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
        }

        /// <summary>
        /// Updates an existing task's title.
        /// </summary>
        /// <param name="email">the logged in user email</param>
        /// <param name="columnOrd">the column ordinal of the task</param>
        /// <param name="taskId">the task's id</param>
        /// <param name="Description">the new description</param>
        public void UpdateTaskDescription(string email, int columnOrd, int taskId, string Description)
        {
            Response res = Service.UpdateTaskDescription(email, columnOrd, taskId, Description);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
        }

        /// <summary>
        /// Updates an existing task's email assignee.
        /// </summary>
        /// <param name="email">the logged in user email</param>
        /// <param name="columnOrd">the column ordinal of the task</param>
        /// <param name="taskId">the task's id</param>
        /// <param name="newAssignee">the new email assignee</param>
        public void UpdateTaskAssignee(string email, int columnOrd, int taskId, string newAssignee)
        {
            Response res = Service.AssignTask(email, columnOrd, taskId, newAssignee);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
        }

        /// <summary>
        /// Update the an existing task's due date.
        /// </summary>
        /// <param name="email">the logged in user email</param>
        /// <param name="columnOrd">the column ordinal of the task</param>
        /// <param name="taskId">the task's id</param>
        /// <param name="dueDate"> new Due date.</param>
        public void UpdateTaskDueDate(string email, int columnOrd, int taskId, DateTime dueDate)
        {
            Response res = Service.UpdateTaskDueDate(email, columnOrd, taskId, dueDate);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
        }


        /// <summary>
        /// Update an existing column's limit .
        /// </summary>
        /// <param name="email">The logged in user</param>
        /// <param name="ColId">The Column's ordinal number</param>
        /// <param name="newLimit">The new Limit</param>
        public void UpdateColumnLimit(string email, int ColId,int newLimit)
        {
            Response res = Service.LimitColumnTasks(email,ColId,newLimit);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
        }

        /// <summary>
        /// Update an existing column's name.
        /// </summary>
        /// <param name="email">The logged in user</param>
        /// <param name="ColId">The Column's ordinal number</param>
        /// <param name="newName">the new Name</param>
        public void UpdateColumnName(string email,int ColId,string newName)
        {
            Response res = Service.ChangeColumnName(email, ColId, newName);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
        }

        /// <summary>
        /// Moves a column left.
        /// </summary>
        /// <param name="email">The logged in user</param>
        /// <param name="ColId">The Column's ordinal number</param>
        public void MoveColumnLeft(string email,int columnOrd)
        {
            Response<Column> res = Service.MoveColumnLeft(email, columnOrd);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);

        }

        /// <summary>
        /// Moves a column right.
        /// </summary>
        /// <param name="email">The logged in user</param>
        /// <param name="ColId">The Column's ordinal number</param>
        public void MoveColumnRight(string email, int columnOrd)
        {
            Response<Column> res = Service.MoveColumnRight(email, columnOrd);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);

        }

        /// <summary>
        /// Adds a new task to the board of the logged in user
        /// </summary>
        /// <param name="email">The logged in user.</param>
        /// <param name="title">the new task's title</param>
        /// <param name="description">The new task's description</param>
        /// <param name="dueDate">The new task's due date</param>
        /// <returns>The task that was added as a TaskModel object.</returns>
        public TaskModel AddTask(string email,string title,string description,DateTime dueDate)
        {
            Response<serviceTask> res = Service.AddTask(email, title, description, dueDate);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
            serviceTask st = res.Value;
            string firstColName = GetFirstColName(email);
            return new TaskModel(this, st.Id, st.CreationTime, st.DueDate, st.Title, st.Description, st.emailAssignee,firstColName);
        }

        /// <summary>
        /// gets the board of the logged in user.
        /// </summary>
        /// <param name="email">the logged in user's email.</param>
        /// <returns>The board of the user as a BoardModel object</returns>
        public BoardModel GetBoard(string email)
        {
            
            Response<Board> res = Service.GetBoard(email);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);

            Board board = res.Value;
            BoardModel boardModel= new BoardModel(this,board.emailCreator,GetColumns(board,email));
            return boardModel;
             
        }

        /// <summary>
        /// Gets the columns of an existing board.
        /// </summary>
        /// <param name="board">The board to get the columns of.</param>
        /// <param name="RequesterEmail">The email of the User that want's it's board.</param>
        /// <returns>An observable collection of ColumnModel with the columns of the board.</returns>
        private ObservableCollection<ColumnModel> GetColumns(Board board,string RequesterEmail)
        {
            IReadOnlyCollection<string> columnsNames = board.ColumnsNames;
            ObservableCollection<ColumnModel> columns = new ObservableCollection<ColumnModel>();

            foreach (string cName in columnsNames)
            {
                Response<Column> res1 = Service.GetColumn(RequesterEmail, cName);
                if (res1.ErrorOccured)
                    throw new Exception(res1.ErrorMessage);
                Column c = res1.Value;
                columns.Add(new ColumnModel(this, c.Name, c.Limit,GetTasks(c)));

            }

            return columns;
        }

        /// <summary>
        /// Gets the tasks of a specific column.
        /// </summary>
        /// <param name="column">the column.</param>
        /// <returns>An observable collection of TaskModel that contains the column's tasks.</returns>
        private ObservableCollection<TaskModel> GetTasks(Column column)
        {
            ObservableCollection<TaskModel> tasks = new ObservableCollection<TaskModel>();
            foreach(serviceTask t in column.Tasks)
            {
                TaskModel taskToAdd = new TaskModel(this, t.Id, t.CreationTime, t.DueDate, t.Title, t.Description, t.emailAssignee,column.Name);
                tasks.Add(taskToAdd);
            }
            return tasks;
        }

        //returns the first column's name.
        public string GetFirstColName(string email)
        {
            Response<Column> res = Service.GetColumn(email, 0);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
            return res.Value.Name;
        }

        /// <summary>
        /// Deletes a column
        /// </summary>
        /// <param name="email">The email of the logged in user.</param>
        /// <param name="colId">The column to delete.</param>
        public void DeleteColumn(string email,int colId)
        {
            Response res = Service.RemoveColumn(email, colId);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
        }

        /// <summary>
        /// Adds a new column to the user's board.
        /// </summary>
        /// <param name="email">The email of the logged in user.</param>
        /// <param name="colId">The column to delete.</param>
        /// <param name="name">The name of the new Column.</param>
        /// <returns>Returns a columnModel object of the new column.</returns>
        public ColumnModel AddColumn(string email,int colId,string name)
        {
            Response<Column> res = Service.AddColumn(email,colId,name);
            if (res.ErrorOccured)
                throw new Exception(res.ErrorMessage);
            Column col = res.Value;
            return new ColumnModel(this, col.Name, col.Limit, GetTasks(col));
        }
    }
}
