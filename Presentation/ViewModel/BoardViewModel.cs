using Presentation.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace Presentation.ViewModel
{
    class BoardViewModel:NotifiableObject
    {
        //Constructor that recieves the current user and shows it's board's details.
        public BoardViewModel(UserModel u):base(u.backendController)
        {
            user = u;
            board = u.Board;
            isHost = u.IsCreator;
            if (isHost)
                hostButtonVisibility = Visibility.Visible;
            else
                hostButtonVisibility = Visibility.Collapsed;


            board.AssignTasksBorderColor(u.Email);
            sortCheck = false;
        }


        //Logs out the current user.
        public bool Logout()
        {
            try
            {
                backendController.Logout(user.Email);
                return true;
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message);
                return false;
            }
        }

        //Represents the selected column of the board.
        private ColumnModel selectedColumn;
        /// <summary>
        /// Update the selected column  when clicked,
        /// Sets the last selected task as null in order to remove the last column's water mark (visually in the listbox)
        /// Updates the visibility of the column panel.
        /// </summary>
        public ColumnModel SelectedColumn { get => selectedColumn;
            set { selectedColumn = value;
                UpdateSelectedColumnExists(value != null);
                ColumnsPanelError = "";
                UpdateColumnPanelTextBoxes();
                if (value != null) SelectedTask = null;
                RaisePropertyChanged("SelectedColumn"); } }


        //Represents the last selected task that was clicked on.
        private TaskModel selectedTask;
        /// <summary>
        /// Update the selected task  when clicked,
        /// Sets the last selected task as null in order to remove the last task's water mark (visually in the listbox)
        /// Updates the visibility of the task panel.
        /// </summary>
        public TaskModel SelectedTask {
            get => selectedTask;
            set {
                
                TasksPanelError = "";
                if (selectedTask != null)
                {
                    
                    selectedTask.ChangeDescriptionLimit();
                    selectedTask = null;
                    RaisePropertyChanged("SelectedTask");
                    
                }
                selectedTask = value;
                if (selectedTask != null)
                {
                    selectedTask.ChangeDescriptionLimit();
                    SelectedColumn = null;
                }
                UpdateSelectedTaskExists(value != null);

                RaisePropertyChanged("SelectedTask");
            }
        }

        //The message that is shown at the buttom of the column panel.
        private string columnsPanelError;
        public string ColumnsPanelError { get => columnsPanelError; set { columnsPanelError = value; RaisePropertyChanged("ColumnsPanelError"); } }

        //The message that is shown at the buttom of the tasks panel.
        private string tasksPanelError;
        public string TasksPanelError { get => tasksPanelError; set { tasksPanelError = value; RaisePropertyChanged("TasksPanelError"); } }

        //Updates the visibility of every controller in the xaml that is related to the selected task.
        private void UpdateSelectedTaskExists(bool exists)
        {
            if (exists && SelectedTask.IsAssignee(User.Email))
                SelectedTaskOfAssigned = Visibility.Visible;
            else
                SelectedTaskOfAssigned = Visibility.Collapsed;
        }

        //Updates the visibility of every controller in the xaml that is related to the selected column.
        private void UpdateSelectedColumnExists(bool exists)
        {
            if (exists)
                SelectedColumnExists = Visibility.Visible;
            else
                SelectedColumnExists = Visibility.Collapsed;
        }

        /// <summary>
        /// Visibility binds:
        /// the visibility of the controllers that are related to the selected task , selected column and 
        /// if the logged in user is the host.
        /// </summary>

        private Visibility _selectedTaskOfAssigned=Visibility.Collapsed;
        public Visibility SelectedTaskOfAssigned { get => _selectedTaskOfAssigned; set { _selectedTaskOfAssigned = value; RaisePropertyChanged("SelectedTaskOfAssigned"); } }

        private Visibility _selectedColumnExists = Visibility.Collapsed;
        public Visibility SelectedColumnExists { get => _selectedColumnExists; set { _selectedColumnExists = value; RaisePropertyChanged("SelectedColumnExists"); } }

        private Visibility hostButtonVisibility;
        public Visibility HostButtonVisibility { get => hostButtonVisibility; }

        private Visibility addColumnPanelVisibility=Visibility.Collapsed;
        public  Visibility AddColumnPanelVisibility { get => addColumnPanelVisibility;
            set { addColumnPanelVisibility = value; RaisePropertyChanged("AddColumnPanelVisibility"); } }

        //The email which the current user want's to assign the selected task to.
        private string assigneToEmail;
        public string AssigneToEmail { get => assigneToEmail; set { assigneToEmail = value; RaisePropertyChanged("AssigneToEmail"); } }

        //The word which the current user want to filter the tasks by.
        private string _filterWord;
        public string FilterWord { get => _filterWord; set { _filterWord = value;} }

        //Represents wether the logged in user is the host or not.
        private bool isHost;
        public bool IsHost { get => isHost; }

        //Holding the user's board as a variant, just for convinency.
        private BoardModel board;
        public BoardModel Board { get => User.Board; }

        //The logged in user.
        private UserModel user;
        public UserModel User { get => user; }

        //Binded to the checkbox of the sort by due date.
        private bool sortCheck;
        //Sorts or unsorts the tasks of the board according to the value .
        public bool SortCheck { get => sortCheck;
            set { sortCheck = value; UpdateTasksSort(value);  RaisePropertyChanged("SortCheck"); } }

        //Binded to the change column name text box.
        private string columnChangedName;
        public string ColumnChangedName { get => columnChangedName; set { columnChangedName = value; RaisePropertyChanged("ColumnChangedName"); } }

        //Binded to the change column limit text box.
        private string newColumnLimit;
        public string NewColumnLimit { get => newColumnLimit; set { newColumnLimit = value; RaisePropertyChanged("NewColumnLimit"); } }

        //Binded to the Add column's name text box.
        private string columnNewName;
        public string ColumnNewName { get => columnNewName; set { columnNewName = value; RaisePropertyChanged("ColumnNewName"); } }

        //Binded to the Add column's limit text box.
        private string newColumnIndex;
        public string NewColumnIndex { get => newColumnIndex; set { newColumnIndex = value; RaisePropertyChanged("NewColumnIndex"); } }

        //throws an exception if a task wasn't selected.
        private void CheckIfSelectedTask() {
            if (SelectedTask == null)
                throw new Exception("No task was selected!");
        }

        //Throws an exception if a column wasn't selected.
        private void CheckIfSelectedColumn()
        {
            if (SelectedColumn== null)
                throw new Exception("No Column was selected!");
        }

        //Filters the columns and shows only the tasks that are meeting the criteria of the filter .
        public void Filter()
        {  
            board.Filter(FilterWord);
            ColumnsPanelError = "";
        }

        /// <summary>
        /// Advances a task to the next column.
        /// First, tries to advance the task from the service layer 
        /// Then, visually advances it to the next column if the service advance method worked.
        /// shows an error message regarding what went wrong if the advance wasn't been able to execute.
        /// </summary>
        internal void AdvanceTask()
        {
            try
            {

                CheckIfSelectedTask();
                ColumnModel column = board.GetColumn(SelectedTask);
                int columnOrd = board.GetColumnId(column);
                backendController.AdvanceTask(user.Email, columnOrd, SelectedTask.Id);
                board.AdvanceTask(SelectedTask, column);
                TasksPanelError = "";//Refreshing the error messages.

            }
            catch (Exception e)
            {
                TasksPanelError = e.Message;
            }

        }

        /// <summary>
        /// Deletes a task from the board.
        /// Tries to delete the task from the service layer,
        /// then, deletes it visually if the service deletion succeeded,
        /// else, shows the appropriate error message.
        /// </summary>
        public void DeleteTask()
        {
            try
            {
                CheckIfSelectedTask();
                ColumnModel column = board.GetColumn(SelectedTask);
                int columnOrd = board.GetColumnId(column);
                backendController.DeleteTask(user.Email, columnOrd, SelectedTask.Id);
                column.RemoveTask(SelectedTask);
                TasksPanelError = "";//Refreshing the error messages.
            }
            catch(Exception e)
            {
                TasksPanelError = e.Message;
            }
        }

        /// <summary>
        /// Switches position of the selected column with the column to it's left.
        /// First, tries to move the column left from the service layer,
        /// then, if the service method succeeded , moves the column left visually,
        /// else , shows the appropriate error message.
        /// </summary>
        public void MoveColumnLeft()
        {
            try
            {
                CheckIfSelectedColumn();
                int columnOrd = board.GetColumnId(SelectedColumn);
                backendController.MoveColumnLeft(User.Email, columnOrd);
                board.MoveColumnLeft(columnOrd,SelectedColumn);
                ColumnsPanelError = "";//Refreshing the error messages.
            }
            catch (Exception e)
            {
                ColumnsPanelError = e.Message;
            }
        }

        /// <summary>
        /// Switches position of the selected column with the column to it's right.
        /// First, tries to move the column right from the service layer,
        /// then, if the service method succeeded , moves the column right visually,
        /// else , shows the appropriate error message.
        /// </summary>
        public void MoveColumnRight()
        {
            try
            {
                CheckIfSelectedColumn();
                int columnOrd = board.GetColumnId(SelectedColumn);
                backendController.MoveColumnRight(User.Email, columnOrd);
                board.MoveColumnRight(columnOrd,SelectedColumn);
                ColumnsPanelError = "";//Refreshing the error messages.
            }
            catch (Exception e)
            {
                ColumnsPanelError = e.Message;
            }
        }

        /// <summary>
        /// Checks if the selected task can be edited.
        /// checks if the selected task is assigned to the current user and if it's not in the last column.
        /// </summary>
        /// <returns> returns the id of the column of the selected task </returns>
        private int CanEditTask() {

            CheckIfSelectedTask();
            if(!SelectedTask.IsAssignee(user.Email))
                throw new Exception("No permissions to edit the task!");
            int colId = SelectedTaskColumnId();
            if (IsLastColumn(colId))
                throw new Exception("Cannot edit tasks in the last column");
            return colId;

        }

        /// <summary>
        /// Returns wether the last column is at the index 'colId'.
        /// </summary>
        /// <param name="colId">the position to check</param>
        /// <returns>true if the position of the last column or false else.</returns>
        private bool IsLastColumn(int colId)
        {
            return board.IsLastColumn(colId);
        }


        /// <summary>
        /// checks if the selected task can be edited, and shows an error in the tasks panel if it can't be.
        /// </summary>
        /// <returns> true if the selected task can be edited or false else.</returns>
        public bool CheckIfCanEditTask()
        {
            try
            {
                CanEditTask();
                return true;
            }
            catch(Exception e)
            {
                TasksPanelError = e.Message;
                return false;
            }
            
        }

        /// <summary>
        /// Gets the selected task's column id.
        /// </summary>
        /// <returns>selected task's column id.</returns>
        public int SelectedTaskColumnId()
        {
            if (SelectedTask != null)
            {
                return board.GetColumnId(board.GetColumn(SelectedTask));
            }
            return -1;
        }

        //returns the current user.
        public UserModel GetUser()
        {
            return this.User;
        }

        /// <summary>
        /// Assign the selected task to another member of the board.
        /// Firstly, tries to change the task assignee from the service layer.
        /// Then, changes the task's assignee in the presentation layer or shows an appropriate error if 
        /// the assignee couldn't be changed from the service.
        /// </summary>
        public void AssignTask()
        {
            try
            {
                int colId = CanEditTask();
                backendController.UpdateTaskAssignee(User.Email, colId, SelectedTask.Id, AssigneToEmail);
                //Refreshing the selected task.
                UpdateTaskEmailAssignee();
                TasksPanelError = "";//Refreshing the error messages.

            }
            catch(Exception e)
            {
                TasksPanelError = e.Message;
            }
        }

        /// <summary>
        /// Updates the email of the selected task's assignee in the presentation layer.
        /// </summary>
        private void UpdateTaskEmailAssignee()
        {
            SelectedTask.EmailAssignee = AssigneToEmail;
            SelectedTask.AssignColor(User.Email);
            AssigneToEmail = "";
            SelectedTask = null;

        }

        /// <summary>
        /// Sorts by due date and the required string of filter,  or unsorts the board 
        /// </summary>
        /// <param name="toSort">if true-> sorts the board , else, unsorts the board.</param>
        private void UpdateTasksSort(bool toSort)
        {
            if (toSort)
                board.SortByDueDate();
            else
                board.Unsort();
        }
        
        /// <summary>
        /// Changes the selected column's name 
        /// Firstly, tries to change the column name from the service layer,
        /// then, changes the name of the column in the presentation layer, or shows an appropriate 
        /// error message if the name couldn't be changed in the service layer.
        /// </summary>
        public void ChangeColumnName()
        {
            try
            {
                if (!String.IsNullOrEmpty(ColumnChangedName))
                {
                    CheckIfSelectedColumn();
                    backendController.UpdateColumnName(User.Email, board.GetColumnId(SelectedColumn), ColumnChangedName);
                    SelectedColumn.ColumnName = ColumnChangedName;
                    ColumnsPanelError = "";//Refreshing the error messages.
                }
            }
            catch(Exception e)
            {
                ColumnsPanelError = e.Message;

            }
        }


        /// <summary>
        /// Changes the selected column's limit 
        /// Firstly, tries to change the column limit from the service layer,
        /// then, changes the limit of the column in the presentation layer, or shows an appropriate 
        /// error message if the limit couldn't be changed in the service layer.
        /// </summary>
        public void ChangeColumnLimit()
        {
            try
            {
                int newLimit;
                if (!String.IsNullOrEmpty(newColumnLimit))
                {
                    if (!int.TryParse(newColumnLimit, out newLimit))
                        throw new Exception("You Must enter a number!");
                    CheckIfSelectedColumn();
                    if (SelectedColumn.Limit == newLimit)
                        throw new Exception("Please enter a different limit than the current one");
                    backendController.UpdateColumnLimit(User.Email, board.GetColumnId(SelectedColumn), newLimit);
                    SelectedColumn.Limit = newLimit;
                    ColumnsPanelError = "";//Refreshing the error messages.
                }
                else
                {
                    throw new Exception("Limit cannot be empty!");
                }
            }
            catch (Exception e)
            {
                ColumnsPanelError = e.Message;
            }
        }

        //Method refreshes the error message in the column panel.
        private void UpdateColumnPanelTextBoxes()
        {
            if (SelectedColumn == null)
            {
                ColumnChangedName = "";
                ColumnChangedName = "";
            }
            else
            {
                ColumnChangedName = SelectedColumn.ColumnName;
                NewColumnLimit = SelectedColumn.Limit.ToString();
            }
            //Updating the visibility of the text box and button in the 'Add Column' Panel;
            UpdateAddColumnPanelVisibility(false);
        }


        /// <summary>
        /// Method updates the visibility of the columns panel .
        /// </summary>
        /// <param name="visible"> Indicates if the panel is visible or invisible.</param>
        private void UpdateAddColumnPanelVisibility(bool visible)
        {
            if (!visible)
                AddColumnPanelVisibility = Visibility.Collapsed;
            else
                AddColumnPanelVisibility = Visibility.Visible;
        }


        /// <summary>
        /// Method Deletes a column.
        /// Firstly, tries to delete the column from the service layer,
        /// then, if the deletion was successful, deletes it also visually from the presentation layer,
        /// else, shows an appropriate error message.
        /// </summary>
        public void DeleteColumn()
        {
            try
            {
                if (SelectedColumn == null)
                {
                    throw new Exception("No column was selected");
                }
                else
                {
                    int index = board.GetColumnId(SelectedColumn);
                    backendController.DeleteColumn(User.Email,index);
                    board.DeleteColumn(index, SelectedColumn);
                }
            }
            catch (Exception e)
            {
                ColumnsPanelError = e.Message;
            }
        }

       
        /// <summary>
        /// Makes the 'Add column panel' visible
        /// </summary>
        public void AddColumn()
        {
            UpdateAddColumnPanelVisibility(true);
        }

        /// <summary>
        /// Makes the 'Add column panel' invisible.
        /// </summary>
        public void CancelColumnAddition()
        {
            UpdateAddColumnPanelVisibility(false);
        }

        /// <summary>
        /// Adds a new column ( new column fields are binded 'newColumnName' and 'newColumnlimit' variables) 
        /// afterwards , makes the column panel invsiible
       /// </summary>
        public void ConfirmNewColumnName()
        {
            try
            {
                if (columnNewName == null)
                    throw new Exception("Name can't be empty!");
                int colIndex;
                if (!int.TryParse(newColumnIndex, out colIndex))
                    throw new Exception("You Must enter a number!");
                ColumnModel ColToAdd=backendController.AddColumn(user.Email, colIndex, columnNewName);
                board.AddColumn(ColToAdd, colIndex);
                UpdateAddColumnPanelVisibility(false);

            }
            catch(Exception e)
            {
                ColumnsPanelError = e.Message;
            }
        }
    }
}
