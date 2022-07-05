using Presentation.Model;
using Presentation.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Presentation.View
{
    /// <summary>
    /// Interaction logic for BoardView.xaml
    /// </summary>
    public partial class BoardView : Window
    {
        BoardViewModel bvm;

        public BoardView(UserModel user)
        {
            InitializeComponent();
            bvm = new BoardViewModel(user);
            this.DataContext = bvm;
            
        }
        //Deletes a task.
        private void DeleteTask(object sender, RoutedEventArgs e)
        {
            bvm.DeleteTask();
        }

        //Redirects to the edit task page .
        //First checks if the redirection can happen and redirects with the current selected task.
        private void EditTask(object sender, RoutedEventArgs e)
        {
            if (bvm.CheckIfCanEditTask())
            {
                TaskModel taskToShow = bvm.SelectedTask;
                int columnIndex = bvm.SelectedTaskColumnId();
                if (taskToShow != null)
                {
                    EditTaskView etv = new EditTaskView(taskToShow,columnIndex,this);
                    etv.Show();
                    this.Hide();
                }
            }
        }

        //Advances a task.
        private void AdvanceTask(object sender, RoutedEventArgs e)
        {
            bvm.AdvanceTask();
        }

        //Move a column left.
        private void MoveColumnLeft(object sender, RoutedEventArgs e)
        {
            bvm.MoveColumnLeft();
        }

        //Move a column right.
        private void MoveColumnRight(object sender, RoutedEventArgs e)
        {
            bvm.MoveColumnRight();
        }

        //Adds a task.
        private void AddTask(object sender, RoutedEventArgs e)
        {
            UserModel user = bvm.GetUser();
            EditTaskView etv = new EditTaskView(user, this);
            etv.Show();
            this.Hide();

        }

        //filters the tasks.
        private void Filter(object sender, RoutedEventArgs e)
        {
            bvm.Filter();
        }

        //Assigns a task to a different user.
        private void AssignTask(object sender, RoutedEventArgs e)
        {
            bvm.AssignTask();
        }

        //Changes a column name.
        private void ChangeColumnName_Click(object sender, RoutedEventArgs e)
        {
            bvm.ChangeColumnName();
        }

        //Changes a column's limit.
        private void ChangeColumnLimit_Click(object sender, RoutedEventArgs e)
        {
            bvm.ChangeColumnLimit();
        }


        //Deletes a column.
        private void DeleteColumn_Click(object sender, RoutedEventArgs e)
        {
            bvm.DeleteColumn();
        }

        //Opens a new window that lets the user add a column.
        private void AddColumn_Click(object sender, RoutedEventArgs e)
        {
            bvm.AddColumn();
        }


        //Cancels a column addition panel.
        private void CancelColumnAddition_Click(object sender, RoutedEventArgs e)
        {
            bvm.CancelColumnAddition();
        }

        //Confirms the addition of a new column.
        private void ConfirmName_Click(object sender, RoutedEventArgs e)
        {
            bvm.ConfirmNewColumnName();
        }

        //Logout
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            if (bvm.Logout())
            {
                LoginView lv = new LoginView(bvm.GetUser());
                lv.Show();
                this.Close();
            }
        }

       
    }
}
