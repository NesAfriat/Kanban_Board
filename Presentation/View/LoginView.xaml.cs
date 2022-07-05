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
using Presentation.ViewModel;
using Presentation.Model;

namespace Presentation.View
{
    /// <summary>
    /// Interaction logic for LoginView.xaml
    /// </summary>
    public partial class LoginView : Window
    {
        private LoginViewModel lvm;
        public LoginView()
        {
            InitializeComponent();
            lvm = new LoginViewModel();
            this.DataContext = lvm;
        }
        
        //A constructor that gets the last user that was logged in.
        public LoginView(UserModel cont)
        {
            InitializeComponent();
            lvm = new LoginViewModel(cont);
            this.DataContext = lvm;
        }
        private void Login_Click(object sender, RoutedEventArgs e)
        {
            UserModel user = lvm.Login();
            if (user != null)
            {
                BoardView bv = new BoardView(user);
                bv.Show();
                this.Close();
            }
        }

        //Redirects to the register page.
        private void Register_Click(object sender, RoutedEventArgs e)
        {
            RegisterView regView = new RegisterView();
            regView.Show();
            this.Close();
        }
    }
}
