using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using DLtask = IntroSE.Kanban.Backend.DataAccessLayer.Objects.Task;

namespace IntroSE.Kanban.Backend.BusinessLayer
{
    class Task : PersistedObject<DLtask>
    {
        private const int titleLimit= 50;
        private  int taskId;
        private DateTime creationTime;
        private string title;
        private string description;
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        private DateTime dueDate;
        private string emailAssignee;

        //Constructor for a new task.
        internal Task(int taskId, DateTime creationTime, string title, string description, DateTime dueDate,long columnId,string emailAs)
        {
            this.emailAssignee = emailAs;
            SetDescription(emailAs,description);
            SetDueDate(emailAs,dueDate);
            SetTitle(emailAs,title);
            this.taskId = taskId;
            this.creationTime = creationTime;
            DLtask dlt =new DLtask(taskId,this.title,this.description,creationTime,this.dueDate,columnId,emailAs);
            dlt.Insert();
            this.id = dlt.Id;
        }
        /// <summary>
        /// a constructor for the LoadDate function
        /// </summary>
        /// <param name="dltask">the DTO object that hold this task's information</param>
        internal Task(DLtask dltask)
        {
            this.id = dltask.Id;
            this.dueDate = dltask.dueDate;
            this.taskId = (int)dltask.taskId;
            this.creationTime = dltask.creationTime;
            this.title = dltask.title;
            this.description = dltask.description;
            this.emailAssignee = dltask.emailAssignee;
        }

        /// <summary>
        /// Assignes a new email Assignee to the current task,
        /// if the user that requests the change is the current  assignee.
        /// </summary>
        /// <param name="emailFrom">The email of the user that requested the change </param>
        /// <param name="emailAssignee">The new email to assign to the current task.</param>
        internal void AssignTask(string emailFrom, string emailAssignee)
        {
            CheckIfAssignee(emailFrom);
            SetEmailAssignee(emailAssignee);
            ToDalObject().emailAssignee = emailAssignee;
        }

        /// <summary>
        /// Checks if the email matches to the email assignee of the current task.
        /// throws an error if it isn't
        /// </summary>
        /// <param name="email"></param>
        internal void CheckIfAssignee(string email)
        {
            if (String.IsNullOrWhiteSpace(email))
            {
                string errorMsg = "The new Assignee email cant be null or empty!";
                log.Warn(errorMsg);
                throw new Exception(errorMsg);
            }
            if (this.emailAssignee.ToLower() != email.ToLower())
            {
                string errorMsg = "This user is not allowed to make changes in the required task";
                log.Warn(errorMsg);
                throw new Exception(errorMsg);
            }
        }
        //Returns the email assignee
        internal string GetEmailAssignee()
        {
            return emailAssignee;
        }
        //Sets the email assignee
        internal void SetEmailAssignee(string newEmail)
        {
            emailAssignee = newEmail.ToLower();
        }
        
        //Getters for the task fields.
        internal virtual int GetTaskId()
        {
            return this.taskId;
        }
        internal DateTime GetCraetionTime()
        {
            return this.creationTime;
        }
        internal string GetTitle()
        {
            return this.title;
        }
        internal string GetDescription()
        {
            return this.description;
        }
        internal DateTime GetDueDate()
        {
            return this.dueDate;
        }
        internal long GetDataBaseId()
        {
            return this.id;
        }

        //Check if the due date is bigget than the current date and sets it as the new due date.
        //Updating the new due date at the data base.
        internal void SetDueDate(string emailUser, DateTime DueDate)
        {
            CheckIfAssignee(emailUser);
            if (DueDate == null)
            {
                log.Error("The due date is null");
                throw new System.ArgumentNullException("The due date argument is null!");
            }
            if (DueDate<DateTime.Now)
            {
                log.Warn("the due date entered should be later than 'now'");
                throw new System.ArgumentException("Due date cant be earlier than today's date!");
            }


            this.dueDate = DueDate;
            if(this.id!=-1)
                ToDalObject().dueDate = dueDate;
        }

        /// <summary>
        /// Sets a new description if it is under 300 letters.
        /// </summary>
        /// <param name="emailUser">The user that requested the change</param>
        /// <param name="newDescription">the new description to set</param>
        internal void SetDescription(string emailUser, string newDescription)
        {
            CheckIfAssignee(emailUser);
            string DescToSet = "";
            if (newDescription != null)
                DescToSet = newDescription;
            if (DescToSet.Length > 300)
            {
                log.Warn("The description should be less than 300 characters");
                throw new System.ArgumentException("Description is too long");
            }
            this.description = DescToSet;
            if(this.id!=-1)
                ToDalObject().description = DescToSet;
        }

        /// <summary>
        /// getting a string and if its between 1 to 50 characters setting it as title
        /// </summary>
        /// <param name="title"> the new title to set for the task</param>
        /// <param name="emailUser">The user that requested the change</param>
        internal void SetTitle(string emailUser,string title)
        {
            CheckIfAssignee(emailUser);
            if (String.IsNullOrWhiteSpace(title) || title.Length > titleLimit)
            {
                log.Warn("The title entered should be less than "+titleLimit+" characters and cannot be empty");
                throw new System.ArgumentException("The task's title cannot be empty and cannot be longer than "+titleLimit+" characters");
            }
            this.title = title;
            if(this.id!=-1)
                ToDalObject().title = title;
        }
        

        /// <summary>
        /// converting the task into dal task
        /// </summary>
        /// <returns>this Task object as Dal.Task obejct</returns>
        internal override DLtask ToDalObject()
        {
            
                DLtask DLt = new DLtask(GetDataBaseId());
                return DLt;
            
        }

        
    }
}
