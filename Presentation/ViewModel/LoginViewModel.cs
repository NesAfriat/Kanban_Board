using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Presentation.Model;
using System.Windows.Media;

namespace Presentation.ViewModel
{
    class LoginViewModel:NotifiableObject
    {

        public LoginViewModel():base(new BackendController())
        {   
        }
            
        //Constructor that recieves the last user that was logged in.
        public LoginViewModel(UserModel u) : base(u.backendController)
        {
        }

        //Binded to the user name text box's text.
        private string _username;
        public string Username
        {
            get => _username;
            set
            {
                this._username = value;
                
                RaisePropertyChanged("Username");
               
            }
        }

        //Binded to the password.
        private string _password;
        public string Password
        {
            get => _password;
            set
            {
                this._password = value;
                RaisePropertyChanged("Password");
            }
        }

        //Binded to the error label's text.
        private string _errorLabelContent;
        public string ErrorLabelContent
        {
            get => _errorLabelContent;
            set
            {
                this._errorLabelContent = value;
                RaisePropertyChanged("ErrorLabelContent");
            }
        }

        /// <summary>
        /// Tries to log in.
        /// returns the user that was logged in or else, shows an appropriate error message saying why 
        /// the log in failed.
        /// </summary>
        /// <returns></returns>
        public UserModel Login()
        {
            try
            {
                 return backendController.Login(Username,Password);
            }
            catch(Exception e)
            {
                ErrorLabelContent =e.Message;
                return null;
            }
        }
        
    }
}
