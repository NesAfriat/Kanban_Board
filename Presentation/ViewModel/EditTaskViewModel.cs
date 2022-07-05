using Presentation.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Media;

namespace Presentation.ViewModel
{
    public class EditTaskViewModel:NotifiableObject
    {
        //current task to edit.
        private TaskModel currTask;

        //current user that edits a task or adds a new task.
        private UserModel user;

        ///Constructor for the page that edits a task.
        public EditTaskViewModel(TaskModel t,int ColId):base(t.backendController)
        {
            currTask = t;
            Title = t.TaskTitle;
            Description = t.Description;
            AssignedEmail = t.EmailAssignee;
            DueDate = t.DueDate;
            this.colId = ColId;
            this.taskId = t.Id;
            firstDueDate = DueDate;
            firstTitle = Title;
            firstDescription = Description;
            Email = t.EmailAssignee;
            DetermineWindoState(true);

        }


        //Constructor for the page that it's purpose is to add a new task.
        public EditTaskViewModel(UserModel u):base(u.backendController)
        {
            DetermineWindoState(false);
            Email = u.Email;
            user = u;
            DueDate = DateTime.Now;
        }

        /// <summary>
        /// Adds a new task
        /// Firstly, tries to add the task from the service layer, 
        /// then, if the addition succeeded, adds it to the user's board,
        /// else, shows an appropriate  error message.
        /// </summary>
        /// <returns></returns>
        public bool AddTask()
        {
            try
            {
                TaskModel newTask=backendController.AddTask(Email, Title, Description, DueDate);
                newTask.AssignColor(user.Email);
                user.AddTask(newTask);
                MessageBox.Show("The task was added successfuly!");
                return true;
            }
            catch(Exception e)
            {
                ErrorLabelContent = e.Message;
                return false;
            }
        }

        //Binded to the label that shows the error while trying to add a task.
        private string errorLabelContent;
        public string ErrorLabelContent { get => errorLabelContent; set { errorLabelContent = value; RaisePropertyChanged("ErrorLabelContent"); } }

        //the email of the current user.
        private string email;
        public string Email { get => email; set { email = value; } }
        
        //The last changed due date.
        private DateTime firstDueDate;
        
        //The last changed title.
        private string firstTitle;

        //The last chagned description.
        private string firstDescription;

        //indicates of the visibility of the add task button.
        private Visibility addTaskButtonsVisibility;
        public Visibility AddTaskButtonsVisibility { get => addTaskButtonsVisibility; set { addTaskButtonsVisibility = value; RaisePropertyChanged("AddTaskButtonsVisibility"); } }

        //indicates of the visibility of the edit task button.
        private Visibility editButtonsVisibility;
        public Visibility EditButtonsVisibility { get => editButtonsVisibility; set { editButtonsVisibility = value; RaisePropertyChanged("EditButtonsVisibility"); } }

        /// <summary>
        /// Determins if the window is meant for an edit of an existing task 
        /// or an addition of a new task.
        /// </summary>
        /// <param name="editState">if true--> the window is in edit mode, else it is meant to add  a task.</param>
        private void DetermineWindoState(bool editState)
        {
            if (editState)
            {
                AddTaskButtonsVisibility = Visibility.Collapsed;
                EditButtonsVisibility = Visibility.Visible;
            }
            else
            {
                AddTaskButtonsVisibility = Visibility.Visible;
                EditButtonsVisibility = Visibility.Collapsed;
            }
        }

        //Binded to the error message for the title .
        private string titleMsg;
        public string TitleMsg { get => titleMsg; set { titleMsg = value; RaisePropertyChanged("TitleMsg"); } }

        //Binded to the error message of the description.
        private string descriptionMsg;
        public string DescriptionMsg { get => descriptionMsg; set { descriptionMsg = value; RaisePropertyChanged("DescriptionMsg"); } }

        //Binded to the error message that indicates of something wrong with the due date.
        private string dueDateMsg;
        public string DueDateMsg { get => dueDateMsg; set { dueDateMsg = value; RaisePropertyChanged("DueDateMsg"); } }

        //The column of the task--> only on edit mode( because there must be an existing task).
        private int colId;
        //The task id of the current task ( on edit mode).
        private int taskId;


        //Binded to the due date of the task.
        private DateTime _dueDate;
        public DateTime DueDate
        {
            get => _dueDate;
            set { _dueDate = value; RaisePropertyChanged("DueDate"); }
        }

        //The title of the task.
        private string _title;
        public string Title
        {
            get => _title;
            set { _title = value; RaisePropertyChanged("Title"); }
        }


        //Binded to the description of the task.
        private string _description;
        public string Description
        {
            get => _description;
            set { _description = value; RaisePropertyChanged("Description"); }
        }

        //Binded to the assigned email of the current task.
        private string _assignedEmail;
        public string AssignedEmail
        {
            get => _assignedEmail;
            set { _assignedEmail = value; RaisePropertyChanged("AssignedEmail"); }
        }

        //Clear the error messages of the title, description and due date.
        private void ResetErrorMsgs()
        {
            DueDateMsg = "";
            DescriptionMsg = "";
            TitleMsg = "";
        }

        //Confirms the changes to the task's fields and updates them.
        //Returns true if all of the changes were made succesfully, or false else.
        public bool ConfirmChanges()
        {
            ResetErrorMsgs();
            if (UpdateTitle() && UpdateDescription() && UpdateDueDate())
                return true;
            return false;
        }

        /// <summary>
        /// Updates the title of the current task.(edit mode)
        /// Firstly, tries to update the title from the service layer,
        /// then,if the update succeeded, updates the task in the presentation layer,
        /// else, shows an appropriate error message.
        /// </summary>
        /// <returns> true if the update succeded , false else.</returns>
        private bool UpdateTitle()
        {
            try
            {
                if (Title != firstTitle)
                {
                    backendController.UpdateTaskTitle(Email, colId, taskId, Title);
                    firstTitle = Title;
                    currTask.TaskTitle = Title;
                    //Successful title message.
                    TitleMsg = "Title was updated successfully!";
                    TitleMsgColor = SuccessfulBrush;

                }
                else
                {
                    TitleMsg = "Title wasn't changed because No changes were made";
                    TitleMsgColor = DefaultBrush;
                }
                    return true;
            }
            catch (Exception e)
            {
                TitleMsg = e.Message;
                TitleMsgColor = FailedBrush;
                return false;
            }
        }
        /// <summary>
        /// Updates the description of the current task.(edit mode)
        /// Firstly, tries to update the title from the service layer,
        /// then,if the update succeeded, updates the task in the presentation layer,
        /// else, shows an appropriate error message.
        /// </summary>
        /// <returns> true if the update succeded , false else.</returns>
        private bool UpdateDescription()
        {
            try
            {
                if (firstDescription != Description)
                {
                    backendController.UpdateTaskDescription(Email, colId, taskId, Description);
                    firstDescription = Description;
                    currTask.Description = Description;

                    DescriptionMsg = "Description was updated successfuly!";
                    DescriptionMsgColor = SuccessfulBrush;
                }
                else
                {
                    DescriptionMsg = "Description was not changed because No Changes Were made";
                    DescriptionMsgColor = DefaultBrush;
                }
                return true;
               
            }
            catch (Exception e)
            {
                DescriptionMsg = e.Message;
                DescriptionMsgColor = FailedBrush;
                return false;
            }
        }

        /// <summary>
        /// Updates the Due date of the current task.(edit mode)
        /// Firstly, tries to update the title from the service layer,
        /// then,if the update succeeded, updates the task in the presentation layer,
        /// else, shows an appropriate error message.
        /// </summary>
        /// <returns> true if the update succeded , false else.</returns>
        private bool UpdateDueDate()
        {
            try
            {
                if (DueDate.Date != firstDueDate.Date)
                {
                    backendController.UpdateTaskDueDate(Email, colId, taskId, DueDate);
                    firstDueDate = DueDate;
                    currTask.DueDate = DueDate;
                    //Successfull due date message.
                    DueDateMsg = "Due date was updated successfully!";
                    DueDateMsgColor = SuccessfulBrush;
                    currTask.AssignForegroundColor();//Re-assigning the foreground color because the due date has changed.

                }
                else
                {
                    DueDateMsg = "Due date wasn't changed because no changes were made";
                    DueDateMsgColor = DefaultBrush;
                }
                    return true;
            }
            catch (Exception e)
            {
                DueDateMsg = e.Message;
                DueDateMsgColor = FailedBrush;
                return false;
            }
        }

        //Some colors for the error messages.
        private SolidColorBrush FailedBrush = Brushes.Red;
        private SolidColorBrush SuccessfulBrush = Brushes.LightSeaGreen;
        private SolidColorBrush DefaultBrush = Brushes.Black;

        //The color of the title error message.
        private Brush titleMsgColor;
        public Brush TitleMsgColor { get=>titleMsgColor; set { titleMsgColor = value; RaisePropertyChanged("TitleMsgColor"); } }

        //The color of the due date error message.
        private Brush dueDateMsgColor;
        public Brush DueDateMsgColor { get => dueDateMsgColor; set { dueDateMsgColor = value; RaisePropertyChanged("DueDateMsgColor"); } }

        //The color of the description error message.
        private Brush descriptionMsgColor;
        public Brush DescriptionMsgColor { get => descriptionMsgColor; set { descriptionMsgColor = value; RaisePropertyChanged("DescriptionMsgColor"); } }

        

        

        


       

        

       

       

        


    }

}


