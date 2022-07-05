using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using IntroSE.Kanban.Backend.BusinessLayer.UserPackage;
using IntroSE.Kanban.Backend.BusinessLayer.BoardPackage;
using IntroSE.Kanban.Backend.DataAccessLayer;
using DlUserController = IntroSE.Kanban.Backend.DataAccessLayer.UserController;
using DLuser= IntroSE.Kanban.Backend.DataAccessLayer.Objects.User;




namespace IntroSE.Kanban.Backend.BusinessLayer
{
    class UserController
    {
        private DlUserController dlUC;
        private const int passwordLowerBound = 5;
        private const int passwordUpperBound = 25;
        private Dictionary<string, User> users;
        private User currentUser;
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        internal UserController()
        {
            dlUC = new DlUserController();
            this.users = new Dictionary<string, User>();
            this.currentUser = null;
        }     
        /// <summary>
        /// Returns the current user if the email is correct and current user is logged in and isn't null.
        /// </summary>
        /// <param name="email">the email of the current user</param>
        /// <returns></returns>
        internal User GetCurrentUser(string email)
        {
            if (email == null)
            {
                log.Warn("An email cant be null!");
                throw new System.ArgumentException("Email cant be null");
            }
            if (currentUser == null)
            {
                log.Error("Cant return a board because there is no user that is logged in");
                throw new Exception("There is no logged in user");
            }
            if (!(currentUser.GetEmail().Equals(email.ToLower())))
            {
                log.Warn("A different user is already logged in");
                throw new Exception("A different user is already logged in");
            }
            if (!currentUser.IsLogged())
            {
                log.Error("Cant return board because the current user isn't logged in");
                currentUser = null;
                throw new Exception("Looks like you are not logged in any more");
            }
            return currentUser;
        }

        /// <summary>
        /// Checks if a user is the board's creator
        /// </summary>
        /// <param name="email">An email of a user</param>
        /// <returns> true- if the email belongs to a user who created the board returns true, else - false</returns>
        internal bool IsCreator(string email)
        {
            if (!EmailExists(email))
            {
                log.Error("User doesn't exist");
                throw new Exception("User doesn't exist");
            }
            return users[email.ToLower()].GetIsCreator();
        }
        /// <summary>
        /// Checks if a user is a board member
        /// </summary>
        /// <param name="email">An email of a user</param>
        /// <param name="board">An input board</param>
        /// <returns> return true- if the email belongs to a user who is a member of a board returns true, else - false</returns>
        internal void CheckIfBoardMember(string email,Board board)
        {
            if (!EmailExists(email))
            {
                log.Warn("Email doesn't exist and therefore is not a member of the board");
                throw new Exception("Email doesn't exists");
            }
            if (!(users[email].GetBoard() == board))
            {
                log.Warn("The email is not of a user with a different board");
                throw new Exception("The user isn't a board member");
            }
        }

        /// <summary>
        /// activate deletion in the data's user controller and deleting the users list and current user.
        /// </summary>
        /// <param name=""></param>
        /// <returns></returns>
        internal void DeleteData()
        {
            dlUC.DeleteAllData();
            users = new Dictionary<string, User>();
            currentUser = null;
        }

        /// <summary>
        /// Method loads all of the users info and adding them to the users to the users diciontary
        /// meaning that the data will be saved on the RAM.
        /// Gets all of the boards data.
        /// </summary>
        internal void LoadData(Dictionary<int,Board> boardsById)
        {
            List<DLuser> dlUsersList = dlUC.SelectAllColumns();

            //First loop--> setting the creators of the boards.
            foreach(DLuser dlu in dlUsersList)
            {

                if (dlu.isCreator)
                {
                    Board board = boardsById[(int)dlu.boardId];
                    board.CreatorEmail = dlu.email.ToLower();
                }

            }
            //Second loop--> After creating completely all of the boards, attaching each user to it's board.
            foreach (DLuser dlu in dlUsersList)
            {
                Board board = boardsById[(int)dlu.boardId];
                User bsu;
                if (dlu.isCreator)
                    bsu = new UserHost(dlu, board);
                else
                    bsu = new UserMember(dlu, board); 

               

                users.Add(bsu.GetEmail(), bsu);
            }
            log.Debug("All data was loaded successfully");
            if (users.Count == 0)
                log.Debug("No users in the data"); 
        }

        
        /// <summary>
        /// Throws an error if the email already exists.
        /// Checks the password according to the client's request for password limitations.
        ///if the email and password are valid-->creates a user and saves it's information on a file and into the users list.
        /// </summary>
        /// <param name="email">the registering user's email</param>
        /// <param name="password">the registering user's password</param>
        /// <param name="nickname">the registering user's nick name</param>
        internal void Register(string email, string password, string nickname)
        {

            CheckValidUserInfo(email, password, nickname);
            User newUser = new UserHost(email.ToLower(), nickname, password);//creating a new user and saving it in the data base.
            users.Add(email.ToLower(), newUser);   
            
            
        }
        /// <summary>
        /// The method creates a new user if the input is valid,
        /// and instead of creating a new board using an existing one - with pointers.
        /// </summary>
        /// <param name="email"></param>
        /// <param name="password"></param>
        /// <param name="nickname"></param>
        /// <param name="email">The email the the Host in order to join to his board</param>
        /// <returns>nothing - throws exceptions if the colussion with an existing users data or illegal inputs</returns>
        internal void RegisterWithExistingBoard(string email, string password, string nickname, string emailHost)
        {
            
            CheckValidUserInfo(email, password, nickname);
            string LowerMail = email.ToLower();
            if (!EmailExists(emailHost))
            {
                log.Warn("The host email does not exist");
                throw new Exception("The host email does not exist");
            }
            string lowerHostMail = emailHost.ToLower();

            User Host = users[lowerHostMail];
            if(!Host.GetIsCreator())
            {
                string errorMsg = "Illegal host email was given";
                log.Error(errorMsg);
                throw new Exception(errorMsg);
            }
            User newUser = new UserMember(LowerMail, nickname, password, Host.GetBoard());
            log.Info("A new board member was created!");
            users.Add(LowerMail, newUser);
        }
        /// <summary>
        /// Method checks if the input of user fields are legal -a helping function for registration
        /// </summary>
        /// <param name="email"></param>
        /// <param name="password"></param>
        /// <param name="nickname"></param>
        /// <returns>nothing - throws exceptions if the colussion with an existing users data or illegal inputs</returns>
        private void CheckValidUserInfo(string email, string password, string nickname)
        {

            if (!IsValidEmail(email))
            {
                log.Warn("Email adress isn't valid");
                throw new Exception("Email adress isn't valid");
            }
            if (EmailExists(email))
            {
                log.Warn("This email already exists");
                throw new Exception("This email already exists");
            }
            CheckPass(password);
            if (!checkNickName(nickname))
            {
                string nickError = "Nickname cannot be empty";
                log.Warn(nickError);
                throw new Exception(nickError);
            }
        }
        /// <summary>
        /// Method if an input email is legal - not an empty or null
        /// </summary>
        /// <param name="nick"></param>
        /// <returns>true if the name is lega, else- false</returns>
        private bool checkNickName(string nick)
        {
            if (String.IsNullOrWhiteSpace(nick))
                return false;
            return true;
        }

        /// <summary>
        /// Method recieves an email and checks if the email is exist the the data.
        /// </summary>
        /// <param name="email"></param>
        /// <returns>return true if the email is of an existing user , else - false</returns>
        private bool EmailExists(string email)
        {
            if (email == null)
                return false;

            string LowerMail = email.ToLower();

            foreach (User u in users.Values)
            {
                if (u.GetEmail().ToLower() == LowerMail)
                {
                    return true;
                }

            }

            return false;
        }
        /// <summary>
        /// Method recieves an email and returns true if the email is valid , or false else.
        /// </summary>
        /// <param name="email"></param>
        /// <returns></returns>
        private bool IsValidEmail(string email)
        {
            if (string.IsNullOrWhiteSpace(email))
                return false;

            try
            {
                // Normalize the domain
                email = Regex.Replace(email, @"(@)(.+)$", DomainMapper,
                                      RegexOptions.None, TimeSpan.FromMilliseconds(200));

                // Examines the domain part of the email and normalizes it.
                string DomainMapper(Match match)
                {
                    // Use IdnMapping class to convert Unicode domain names.
                    var idn = new IdnMapping();

                    // Pull out and process domain name (throws ArgumentException on invalid)
                    var domainName = idn.GetAscii(match.Groups[2].Value);

                    return match.Groups[1].Value + domainName;
                }
            }
            catch (RegexMatchTimeoutException e)
            {
                return false;
            }
            catch (ArgumentException e)
            {
                return false;
            }

            try
            {
                return Regex.IsMatch(email,
                    @"^(?("")("".+?(?<!\\)""@)|(([0-9a-z]((\.(?!\.))|[-!#\$%&'\*\+/=\?\^`\{\}\|~\w])*)(?<=[0-9a-z])@))" +
                    @"(?(\[)(\[(\d{1,3}\.){3}\d{1,3}\])|(([0-9a-z][-0-9a-z]*[0-9a-z]*\.)+[a-z0-9][\-a-z0-9]{0,22}[a-z0-9]))$",
                    RegexOptions.IgnoreCase, TimeSpan.FromMilliseconds(250));
            }
            catch (RegexMatchTimeoutException)
            {
                return false;
            }
        }

        /*Password requirements:
         * Length between 4 and 20 letter.
         * At least one capital letter
         * At least one lower case letter.
         * At least one digit
         */
        private void CheckPass(string pass)
        {
            if (String.IsNullOrWhiteSpace(pass))
                throw new Exception("Password can't be empty!");
            bool ans = true;
            bool digit = false;//indicates if the password contains a digit.
            bool smallLetter = false;//indicates if the password contains a lower case letter.
            bool upperCase = false;//indicates if the password contains an upper case letter.
            if ((pass.Length < passwordLowerBound) || (pass.Length > passwordUpperBound))
                ans = false;
            else
            {
                for (int i = 0; i < pass.Length; i++)
                {
                    if (!upperCase && char.IsUpper(pass[i]))
                        upperCase = true;
                    else if (!digit && char.IsDigit(pass[i]))
                        digit = true;
                    else if (!smallLetter && char.IsLower(pass[i]))
                        smallLetter = true;
                }

                if (!(digit & upperCase & smallLetter))
                    ans = false;
            }
            if (!ans)
            {
                throw new Exception("Password must be at least 5 characters and contain an upper case letter, a lower case letter and a digit");
            }
        }

        /// <summary>
        /// throws an exception if the user doesnt exist or password and email dont match.
        /// if email and password match--> changing the current user to the user that logged in,
        /// and changes the user's logged in field to true.
        /// </summary>
        /// <param name="email">the email of the user that tries to log in</param>
        /// <param name="password">the password of the user that tries to log in</param>
        internal User Login(string email, string password)
        {
            
            if (currentUser != null)
            {
                log.Error("A user is already logged in");
                throw new Exception("There is a logged in user already!");
            }
            if (email == null)
            {
                log.Warn("Email cannot be empty");
                throw new Exception("Email cannot be empty!");
            }
            string lowerMail = email.ToLower();
            if (!EmailExists(email) || !((users[lowerMail]).GetPassword().Equals(password)))
            {
                log.Warn("Email doesn't exist or Email and Password are wrong");
                throw new Exception("Wrong email or password");
            }
            
                users[lowerMail].Login();
                currentUser = users[lowerMail];
            return currentUser;
                
            //Check if user is connected in db.
            
        }

        /// <summary>
        /// changes logged in field to false and Current user field in userController to null.
        /// </summary>
        /// <param name="email"> the email of the current user</param>
        internal void LogOut(string email)
        {
            if (currentUser == null)
            {
                log.Error("There is no user that is logged in");
                throw new Exception("There is no user that is logged in");
            }
            User curr = GetCurrentUser(email);
            curr.Logout();
            currentUser = null;
            
        }
        
        /// <summary>
        /// returns a user's board.
        /// </summary>
        /// <param name="email">The email of the current user</param>
        /// <returns> user's board </returns>
        internal Board GetBoard(string email)
        {
            return GetCurrentUser(email).GetBoard();
        }

       

    }
}
