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
    /// Interaction logic for RegisterView.xaml
    /// </summary>
    public partial class RegisterView : Window
    {
        private RegisterViewModel rvm;
        public RegisterView()
        {
            InitializeComponent();
            rvm = new RegisterViewModel();
            this.DataContext = rvm;
        }

        
        private void Register_Click(object sender, RoutedEventArgs e)
        {
            if (rvm.Register()) {
                LoginView lv = new LoginView();
                lv.Show();
                this.Close();
            }
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            LoginView lv = new LoginView();
            lv.Show();
            this.Close();
        }
    }
}
