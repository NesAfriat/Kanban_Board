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
    /// Interaction logic for EditTaskView.xaml
    /// </summary>
    public partial class EditTaskView : Window
    {
        private EditTaskViewModel etvm;
        private Window previousWindow;

        //Constructor for the page that edits a task.
        public EditTaskView(TaskModel t,int colId,Window prev)
        {
            InitializeComponent();
            etvm = new EditTaskViewModel(t,colId);
            this.DataContext = etvm;
            this.previousWindow = prev;

        }

        //Constructor for the page that adds a task.
        public EditTaskView(UserModel user,Window prev)
        {
            InitializeComponent();
            etvm = new EditTaskViewModel(user);
            this.DataContext = etvm;
            this.previousWindow = prev;

        }

        //Cancels the task addition and redirects to the Previous Window.
        private void CancelClick(object sender, RoutedEventArgs e)
        {
            previousWindow.Show();
            this.Close();
            
        }

        //Confirms the changes and edits the current task's info.
        private void ConfirmChanges(object sender, RoutedEventArgs e)
        {
            etvm.ConfirmChanges();
        }

        //Adds a new task.
        private void AddTask(object sender, RoutedEventArgs e)
        {
            if(etvm.AddTask())
            {
                previousWindow.Show();
                this.Close();
            }
            
        }

        //Opens the board view if this window is forced to be closed.
        protected override void OnClosed(EventArgs e)
        {
            base.OnClosed(e);

            previousWindow.Show();
        }
    }
}
