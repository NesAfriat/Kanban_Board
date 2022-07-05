using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Presentation.Model
{
    public class ColumnModel:NotifiableModelObject
    {
        //Constructor for a new column model.
        public ColumnModel(BackendController bc,string name,int limit,ObservableCollection<TaskModel> tasks) : base(bc)
        {
            this.tasks = tasks;
            this.filteredTasks = tasks;
            this.columnName = name;
            this.limit = limit;
        }

        //Holds the column name of the current column.
        private string columnName;
        public string ColumnName { get => columnName;
            set {
                if(value!=columnName)
                {
                    foreach (TaskModel task in tasks)
                        task.ColumnName = value;
                }
                columnName = value;
                RaisePropertyChanged("ColumnName");
            }
        }
        //The current column's limit.
        private int limit;
        public int Limit { get => limit;
            set { limit = value;
                RaisePropertyChanged("Limit");
            }
        }

        /// <summary>
        /// The collection of the tasks -->stays as it is and doesn't changes
        /// </summary>
        private ObservableCollection<TaskModel> tasks;
        //The collection of the filtered tasks--> these are the tasks collection that is shown
        //And on which the filters occurs.
        private ObservableCollection<TaskModel> filteredTasks;
        public ObservableCollection<TaskModel> Tasks { get => filteredTasks; private set { filteredTasks = value;  } }

        private string _filterWord;
        public string FilterWord { get => _filterWord; set { _filterWord = value; FilterTasks(); } }

        /// <summary>
        /// Filters the tasks by the filter word .
        /// </summary>
        private void FilterTasks()
        {
            if (String.IsNullOrEmpty(FilterWord))
                filteredTasks = tasks;
            else
            {
                filteredTasks = new ObservableCollection<TaskModel>();
                foreach(TaskModel t in tasks)
                {
                    if (t.Filter(FilterWord))
                        filteredTasks.Add(t);
                        
                }
            }

            if (IsSorted)
                SortByDueDate();
            else 
                RaisePropertyChanged("Tasks");

        }


        /// <summary>
        /// Sort the tasks by their due date.
        /// </summary>
        public void SortByDueDate()
        {
            IsSorted = true;
            ObservableCollection<TaskModel> sortedTasks= new ObservableCollection<TaskModel>();
            foreach(TaskModel t in Tasks.OrderBy(p=>p.DueDate))
            {
                sortedTasks.Add(t);                    
            }
            Tasks = sortedTasks;
            RaisePropertyChanged("Tasks");
        }

        /// <summary>
        /// Unsorts the tasks of this column -->Returns them to the original order.
        /// </summary>
        public void Unsort()
        {
            IsSorted = false;
            //Refiltering by the filter word.
            FilterWord = _filterWord;
        }


        private bool IsSorted;


        /// <summary>
        /// Refiltering after a change has happened.
        /// </summary>
        private void NotifyOnTasksUpdate()
        {
            FilterTasks();
        }

        /// <summary>
        /// Advances a task from this column to the nextCol.
        /// Removes the task from the current column and inserts it to the next.
        /// </summary>
        /// <param name="task">The task to advance</param>
        /// <param name="nextCol">The column to add the task to.</param>
        public void AdvanceTask(TaskModel task, ColumnModel nextCol)
        {
            RemoveTask(task);
            nextCol.AddTask(task);//Using AddTask() in order to notify of the change in the next column.
            task.ColumnName = nextCol.ColumnName;

        }
        /// <summary>
        /// Private method that removes a task from the tasks collection.
        /// </summary>
        /// <param name="task"> The task to remove</param>
        private void DeleteTask(TaskModel task)
        {
            int taskIndex = tasks.IndexOf(task);
            if (taskIndex != -1)
                tasks.RemoveAt(taskIndex);

        }

        /// <summary>
        /// Deletes a task and notifies the changes.
        /// </summary>
        /// <param name="task">The task to delete</param>
        public void RemoveTask(TaskModel task)
        {
            DeleteTask(task);
            NotifyOnTasksUpdate();
        }

        /// <summary>
        /// Private method that adds a task to the tasks collection.
        /// </summary>
        /// <param name="task">The task to add</param>
        private void InsertTask(TaskModel task)
        {
            bool exists = tasks.IndexOf(task) != -1;
            if (!exists)
                tasks.Add(task);
            
        }
        /// <summary>
        /// Adds a task and notifies of the changes.
        /// </summary>
        /// <param name="task">The task to add.</param>
        public void AddTask(TaskModel task)
        {
            InsertTask(task);
            NotifyOnTasksUpdate();
        }

        /// <summary>
        /// Assigns the border and background color of the tasks according to the logged in email.
        /// </summary>
        /// <param name="email">The logged in user's email.</param>
        public void AssignTasksColor(string email)
        {
            foreach (TaskModel t in tasks)
            {
                t.AssignColor(email);
            }
        }

        /// <summary>
        /// Adds a task to the current column
        /// </summary>
        /// <param name="tasksToAdd">The task to add</param>
        public void AddTasks(ObservableCollection<TaskModel> tasksToAdd)
        {
            //Checking for cautious, the method is being called only after validating that 
            //the number of tasks can be added.
            if(Limit>=tasks.Count + tasksToAdd.Count)
            {
                foreach(TaskModel task in tasksToAdd)
                {
                    this.tasks.Add(task);
                    task.ColumnName = this.ColumnName;//updating the column name of each task.
                }
                NotifyOnTasksUpdate();
            }
        }
    }
}
