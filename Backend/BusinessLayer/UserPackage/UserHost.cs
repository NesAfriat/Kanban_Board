using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using IntroSE.Kanban.Backend.BusinessLayer.BoardPackage;
using DalUser = IntroSE.Kanban.Backend.DataAccessLayer.Objects.User;



namespace IntroSE.Kanban.Backend.BusinessLayer.UserPackage
{
    internal class UserHost:User
    {

        /// <summary>
        /// Constructor for a new User Host
        /// creates a new board and inserts it to the data base.
        /// </summary>
        /// <param name="email">The email of the new user.</param>
        /// <param name="nick">The nickname of the new user</param>
        /// <param name="password">The password of the new user</param>
        internal UserHost(string email, string nick, string password):base(email,nick,password)
        {
            
            this.myBoard = new BoardHost(email);
            DalUser newUser = new DalUser(email.ToLower(), nick, password, this.myBoard.GetDataBaseID(), true);
            newUser.Insert();
            this.id = newUser.Id;
        }

        /// <summary>
        /// A construction that gets a Dal user and it's related BS board and creates a business user accordingly
        /// Creates a Board host for the user host.
        /// </summary>
        /// <param name="dluser">the Dal user </param>
        /// <param name="myBoard"> the BS Board</param>
        internal UserHost(DalUser dluser, Board myBoard):base(dluser) 
        {
            this.myBoard = new BoardHost(myBoard);
        }


        //Returns true because the user is host.
        internal override bool GetIsCreator()
        {
            return true;
        }
    }
}
