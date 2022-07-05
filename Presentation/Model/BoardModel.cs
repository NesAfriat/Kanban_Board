using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Presentation.Model
{
    public class BoardModel:NotifiableModelObject
    {
        
        //Proprerty-> the email of the creator of the board.
        private string hostEmail;
        public string HostEmail { get => hostEmail;
            private set { }
        }

        //The collection of the columns that the board has.
        private ObservableCollection<ColumnModel> columns;
        public ObservableCollection<ColumnModel> Columns
        {
            get => columns;
            private set { columns = value; RaisePropertyChanged("Columns"); } 
        }

        /// <summary>
        /// checks if the colId is the last column
        /// </summary>
        /// <param name="colId">The id of a column</param>
        /// <returns>true if the colId is the last Column's column id (The column id is the position in the collection)</returns>
        public bool IsLastColumn(int colId)
        {
            return columns.Count-1 == colId;
                
        }

        /// <summary>
        /// Assigns the color of the border for each task in each column,
        /// which depends on the logged in user.
        /// </summary>
        /// <param name="email">The logged in user's email</param>
        public void AssignTasksBorderColor(string email)
        {
            foreach (ColumnModel c in columns)
                c.AssignTasksColor(email);
        }

        //constructor
        public BoardModel(BackendController bc, string hostEmail, ObservableCollection<ColumnModel> columns) : base(bc)
        {

            this.hostEmail = hostEmail;
            this.columns = columns;
        }

        //Filters all of the tasks by the filter word.
        public void Filter(string filterBy)
        {
            foreach(ColumnModel c in columns)
            {
                c.FilterWord = filterBy;
            }
        }

        /// <summary>
        /// Returns the id of the column
        /// </summary>
        /// <param name="column">The column which we want it's id</param>
        /// <returns> The position of the column in collection ( which represents the column's id</returns>
        public int GetColumnId(ColumnModel column)
        {
             
            return columns.IndexOf(column);
        }

        /// <summary>
        /// gets the column of the task.
        /// </summary>
        /// <param name="task">The task which we want its column </param>
        /// <returns>The column that contains the task.</returns>
        public ColumnModel GetColumn(TaskModel task)
        {
            foreach (ColumnModel c in columns)
            {
                if (c.ColumnName == task.ColumnName)
                {
                    return c;
                }
            }
            return null;
        }

        /// <summary>
        /// Gets the column next to the column in the argument.
        /// </summary>
        /// <param name="column">The column which we want's its right neighbore column</param>
        /// <returns>The next column</returns>
        private ColumnModel GetNextColumn(ColumnModel column)
        {
            int colId = columns.IndexOf(column);
            return columns[colId+1];
        }

        /// <summary>
        /// Advances a task in the column gotten in the argument to the next column.
        /// </summary>
        /// <param name="task">The task to advance.</param>
        /// <param name="column">The column which contains the task</param>
        public void AdvanceTask(TaskModel task,ColumnModel column)
        {
            ColumnModel nextCol = GetNextColumn(column);
            column.AdvanceTask( task,  nextCol);
            
        }

        /// <summary>
        /// Moves the column left.
        /// </summary>
        /// <param name="index">The index of the current column</param>
        /// <param name="column">The column to move left.</param>
        public void MoveColumnLeft(int index,ColumnModel column)
        {
            if (index >0)
            {
                columns.Remove(column);
                columns.Insert(index - 1, column);
            }
        }

        /// <summary>
        /// Moves the column right.
        /// </summary>
        /// <param name="index">The index of the current column</param>
        /// <param name="column">The column to move right.</param>
        public void MoveColumnRight(int index,ColumnModel column)
        {
            if (index < columns.Count - 1)
            {
                columns.Remove(column);
                columns.Insert(index + 1, column);
            }
        }

        /// <summary>
        /// ADds a task to the first column of the board.
        /// </summary>
        /// <param name="task">The task to add</param>
        public void addTask(TaskModel task)
        {
            columns[0].AddTask(task);
        }

        /// <summary>
        /// Sorts every column's tasks by their due date.(Changes the positions in the collections).
        /// </summary>
        public void SortByDueDate()
        {
            foreach(ColumnModel c in columns)
            {
                c.SortByDueDate();
            }
        }

        /// <summary>
        /// Returns the tasks of the columns to their original order.
        /// </summary>
        public void Unsort()
        {
            foreach(ColumnModel c in columns)
            {
                c.Unsort();
            }
        }

        /// <summary>
        /// Deletes a column and transfer its tasks to the left column of it.
        /// </summary>
        /// <param name="colIndex">The index of the column to delete</param>
        /// <param name="column">The column to delete</param>
        public void DeleteColumn(int colIndex,ColumnModel column)
        {
            ObservableCollection<TaskModel> tasks = column.Tasks;
            DeleteColumn(colIndex);
            if (colIndex == 0)
                columns[0].AddTasks(tasks);
            else
                columns[colIndex-1].AddTasks(tasks);

        }

        /// <summary>
        /// Deletes a column without transffering its tasks.
        /// </summary>
        /// <param name="ColId">The column id to remove</param>
        private void DeleteColumn(int ColId)
        {
            columns.RemoveAt(ColId);
            RaisePropertyChanged("Columns");
        }

        /// <summary>
        /// Adds a column at a specific index to the board.
        /// </summary>
        /// <param name="col">The new Column to add</param>
        /// <param name="colId">The id to add the column at.</param>
        public void AddColumn(ColumnModel col,int colId)
        {
            IEnumerator<ColumnModel> iter = columns.GetEnumerator();
            ObservableCollection<ColumnModel> newColumns = new ObservableCollection<ColumnModel>();

            bool flag = false;
            for (int i = 0; i < columns.Count;i++)
            {
                iter.MoveNext();
                if (i == colId)
                {
                    newColumns.Add(col);
                    flag = true;
                }
                newColumns.Add(iter.Current);
            }
            if (!flag)
                newColumns.Add(col);
            Columns = newColumns;
            

        }
    }
}
