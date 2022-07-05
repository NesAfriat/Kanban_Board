using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace Presentation.ViewModel
{
    class RegisterViewModel:NotifiableObject
    {
        public RegisterViewModel() : base(new BackendController()) { }


        //Binded to the uesrname text box text.
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

        //Binded to the password's text boxe's text.
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


        //Binded to the nickname text boxe's text.
        private string _nickname;
        public string Nickname
        {
            get => _nickname;
            set
            {
                _nickname = value;
                RaisePropertyChanged("Nickname");
            }
        }

        //Binded to the email of the host.
        private string _hostEmail;
        public string HostEmail
        {
            get => _hostEmail;
            set
            {
                _hostEmail = value;
                RaisePropertyChanged("HostEmail");
            }
        }

        //Binded to the check boxe's check mark.
        private bool _checkBox=false;
        public bool JoinBoardCheckBox
        {
            get => _checkBox;
            set
            {
                _checkBox = value;
                RaisePropertyChanged("CheckBox");
                ChangeVisibility(value);
            }
        }

        //Indicates wether the host email's text box is visible.
        private Visibility _hostEmailVisibility=Visibility.Hidden;
        public Visibility HostEmailVisibility
        {
            get => _hostEmailVisibility;
            set
            {
                _hostEmailVisibility = value;
                RaisePropertyChanged("HostEmailVisibility");
            }
        }

        /// <summary>
        /// Changes the visibility of the Host email text box according.
        /// </summary>
        /// <param name="visible">shows the host email text box if true, else doesn't show.</param>
        private void ChangeVisibility(bool visible)
        {
            Visibility v = Visibility.Hidden;
            if (visible)
                v = Visibility.Visible;

            HostEmailVisibility = v;
        }

        

        //Binded to the error label's message.
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
        /// Registers.
        /// register a new user if the 'Join an existing board' check box is false,
        /// or registers a to an existing board if it's true.
        /// </summary>
        /// <returns></returns>
        public bool Register()
        {
            try
            {
                if (JoinBoardCheckBox)
                    backendController.Register(Username, Password, Nickname, HostEmail);
                else
                    backendController.Register(Username, Password, Nickname);

                return true;

            }
            catch (Exception e)
            {
                ErrorLabelContent = e.Message;
                return false;
            }
        }
    }


}
