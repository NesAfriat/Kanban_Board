using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
[assembly: InternalsVisibleTo("DynamicProxyGenAssembly2")]
namespace IntroSE.Kanban.Backend.DataAccessLayer.Objects
{
    internal class Task : DTO
    {

        internal long taskId;
        internal DateTime creationTime;
        private string Title;
        private string Description;
        private DateTime DueDate;
        private long ColumnId;
        private string EmailAssignee;
        //Setters for the fields that update them in the data base.
        internal string title { get => Title; set { Update(_TitleColumn, value); } }
        internal string description { get => Description;  set { Update(_DesctiptionColumn, value); } }
        internal DateTime dueDate { get => DueDate; set { Update(_DueDateColumn, value.ToString()); } }
        internal long columnId { get => ColumnId; set { Update(_ColumnIdColumn, value); } }
        internal string emailAssignee { get => EmailAssignee; set { Update(_EmailAssigneeColumn, value); } }
        //Columns names in the data base.
        internal const string _TaskIdColumn = "TaskID";
        internal const string _TitleColumn = "Title";
        internal const string _DesctiptionColumn = "Description";
        internal const string _CreationTimeColumn = "CreationTime";
        internal const string _DueDateColumn = "DueDate";
        internal const string _ColumnIdColumn = "ColumnId";
        internal const string _EmailAssigneeColumn = "EmailAssignee";
        /// <summary>
        /// A dal class for the Task object, gets the data of the dal type columns and the max id
        /// of tasks for each board - as counter for its' id's
        /// </summary>
        /// 

        //A constructor for a new task.
        public Task(long taskId,long id, string title, string description, DateTime creationTime, DateTime dueDate,long ColumnId,string emailAs):base(new TaskController())
        {
            this.Id = id;
            this.DueDate = dueDate;
            this.ColumnId = ColumnId;
            this.taskId = taskId;
            this.creationTime = creationTime;
            this.Title = title;
            this.Description = description;
            this.EmailAssignee = emailAs;
        }

        //A Constructor for an existing task, that generates it's id taken from the data base.
        public Task(long taskId, string title, string description, DateTime creationTime, DateTime dueDate, long ColumnId,string emailAs) : base(new TaskController())
        {
            this.Id = GetMaxId()+1;
            this.DueDate = dueDate;
            this.ColumnId = ColumnId;
            this.taskId = taskId;
            this.creationTime = creationTime;
            this.Title = title;
            this.Description = description;
            this.EmailAssignee = emailAs;
        }

        //A consturctor to update the task. Only id needed in order to update the task.
        public Task(long Id):base(new TaskController())
        {
            this.Id = Id;
        }
        
    }
}
