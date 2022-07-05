using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Media;

namespace Presentation.Model
{
    public class TaskModel: NotifiableModelObject
    {
        //Constructor for a new task .
        public TaskModel(BackendController bc,int id,DateTime creation, DateTime due,string title,string description,string emailAssignee,string cName) : base(bc)
        {
            this.columnName = cName;
            this.id = id;
            this.creationTime = creation.Date;
            this.dueDate = due.Date;
            this.title = title;
            this.description = description;
            this.emailAssignee = emailAssignee;
        }

        //Holds the max description size to show.
        private int maxDescriptionSize = defaultMaxDesc;
        //Holds the defaul max description size.
        private const int defaultMaxDesc = 50;

        /// <summary>
        /// Cancel the limit on the description which causes to show all of the current task description.
        /// </summary>
        private void CancelDescriptionLimit()
        {
            int length = 0;
            if (description != null)
                length = description.Length;
            maxDescriptionSize = length+1;
            RaisePropertyChanged("Description");
        }
        /// <summary>
        /// Returns the description limit to the default size.
        /// </summary>
        private void ReturnDescriptionLimit()
        {
            maxDescriptionSize = defaultMaxDesc;
            RaisePropertyChanged("Description");

        }

        /// <summary>
        /// Changes the description limit
        /// if it was default--> returns it to default
        /// else cancel the description limit.
        /// </summary>
        public void ChangeDescriptionLimit()
        {
            if (maxDescriptionSize == defaultMaxDesc)
                CancelDescriptionLimit();
            else
                ReturnDescriptionLimit();
        }

        /// <summary>
        /// Checks if the task has a certain string contained in the title or the description.
        /// </summary>
        /// <param name="filterBy">The string we check if the task contains.</param>
        /// <returns>true if the task contains the stirng, false else.</returns>
        public bool Filter(string filterBy)
        {
            if (String.IsNullOrEmpty(filterBy)) 
                return true;

            string filterWord = filterBy.ToLower();
            return title.ToLower().Contains(filterWord) || description.ToLower().Contains(filterWord);
        }
        
        //the id of the current task
        private int id;
        public  int Id { get => id;}

        //Some colors for the changes.
        private SolidColorBrush defaultBackground = Brushes.White;
        private SolidColorBrush assigneeColor = Brushes.Blue;
        private SolidColorBrush notAssigneeColor = Brushes.Black;
        private SolidColorBrush AlmostOverColor = Brushes.Orange;
        private SolidColorBrush OverColor = Brushes.Crimson;

        //The border color of the task.
        private Brush borderColor=Brushes.Black;
        public Brush BorderColor { get => borderColor; set { borderColor = value; RaisePropertyChanged("BorderColor"); } }

        //The background color of the task.
        private Brush taskBackground;
        public Brush TaskBackground { get => taskBackground; set { taskBackground = value; RaisePropertyChanged("TaskBackground");  } }

        /// <summary>
        /// Changes the colors of the background and the border according to the logged in user's email.
        /// </summary>
        /// <param name="email">the email of the logged in user</param>
        public void AssignColor(string email)
        {
            AssignBorderColor(email);
            AssignForegroundColor();
        }

        /// <summary>
        /// assigning the border color of the task according to the logged in user's email
        /// </summary>
        /// <param name="email">the logged  in user's email</param>
        private void AssignBorderColor(string email)
        {
            if (IsAssignee(email))
                BorderColor = assigneeColor;
            else
                BorderColor = notAssigneeColor;
        }

        /// <summary>
        /// assigning the foreground color of the task according to the logged in user's email
        /// </summary>
        /// <param name="email">the logged  in user's email</param>
        public void AssignForegroundColor()
        {
            if ((DueDate <= DateTime.Now))
                TaskBackground = OverColor;
            else
            {
                TimeSpan past = DateTime.Now - creationTime;
                TimeSpan fullSpan = dueDate - creationTime;
                if (past.TotalMinutes >= (fullSpan.TotalMinutes* 3 / 4))
                    TaskBackground = AlmostOverColor;
                else
                    TaskBackground = defaultBackground;
            }

        }

        //The name of the column that conatins the task.
        private string columnName;
        public string ColumnName { get => columnName; set { columnName = value; } }

        //The creation time
        private DateTime creationTime;
        public DateTime CreationTime { get=>creationTime; }

        //The due date of the task
        private DateTime dueDate;
        public DateTime DueDate { get => dueDate; set { dueDate = value; RaisePropertyChanged("DueDate"); } }

        //The task's title.
        private string title;
        public string TaskTitle { get => title; set { title = value; RaisePropertyChanged("taskTitle"); } }

        //The task's description
        private string description;
        //Returns the description's substring to the Current description limit.
        public string Description {
            get {
                string descToReturn = description;
                if (description != null && description.Length > maxDescriptionSize)
                {
                   descToReturn = description.Substring(0, maxDescriptionSize) + "...";
                }
                return descToReturn;
                 }
            set { description = value; RaisePropertyChanged("Description"); } }

        //The task's emailAssignee.
        private string emailAssignee;
        public string EmailAssignee { get => emailAssignee; set { emailAssignee = value; RaisePropertyChanged("EmailAssignee"); } }

        /// <summary>
        /// returns true if the email is the email assignee.
        /// </summary>
        /// <param name="email">The email to check if equals to the assignee email.</param>
        /// <returns></returns>
        public bool IsAssignee(string email)
        {
            return email.ToLower() == emailAssignee.ToLower();
        }
    }

}
