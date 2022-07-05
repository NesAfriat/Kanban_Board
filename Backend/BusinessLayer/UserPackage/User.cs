using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DalUser = IntroSE.Kanban.Backend.DataAccessLayer.Objects.User;
namespace IntroSE.Kanban.Backend.BusinessLayer.UserPackage
{
    abstract class User : PersistedObject<DalUser>
    {
        protected string email;
        protected string nickname;
        protected bool loggedIn;
        protected string password;
        protected Board myBoard;
        
        /// <summary>
        /// A construction that gets a Dal user .
        /// This constructor does not set the board, because the subclasses most do it accordingly with their needs.
        /// </summary>
        /// <param name="dluser">the Dal user </param>
        internal User(DalUser dluser) //converting from Dal User to business layer user.
        {
            this.id = dluser.Id;
            this.email = dluser.email.ToLower();
            this.password = dluser.password;
            this.nickname = dluser.nickname;
            this.loggedIn = false;
        }

        /// <summary>
        /// A constructor for a new user.
        /// This constructor does not set the board of the user, this is the responsibility of the subclasses.
        /// </summary>
        /// <param name="email">The new user's email</param>
        /// <param name="nick">The new user's nicknamae</param>
        /// <param name="password">The new user's password</param>
        internal User(string email, string nick, string password)
        {
            string lowerEmail = email.ToLower();
            this.email = lowerEmail;
            this.password = password;
            this.nickname = nick;
            this.loggedIn = false;

        }

        internal User() { }

        //returns wether the user is a creator or not.
        internal abstract bool GetIsCreator();
        
       
       //Getters.
        internal string GetEmail()
        {
            return this.email;
        }
        internal string GetNickname()
        {
            return this.nickname;
        }
        internal Board GetBoard()
        {
            return this.myBoard;
        }
        internal string GetPassword()
        {
            return this.password;
        }
        
        //Changes the loggedIn Field to true.
        internal void Login()
        {
           
            this.loggedIn = true;
        }
        //Changes the loggedIn field to false.
        internal void Logout()
        {
            
            this.loggedIn = false;
        }

        //Returns the loggedIn field.
        internal bool IsLogged()
        {
            return loggedIn;
        }
        /// <summary>
        /// ovveride action from persistent - getting the dto user that matches this user
        /// </summary>
        /// <param name="id">the user keeps his id in persistent class that points his data in the sql table - the key</param>
        /// <returns>return a dto user that matches this users id</returns>
        internal override DalUser ToDalObject()
        {
            return new DalUser(id);
        }
    }
}
