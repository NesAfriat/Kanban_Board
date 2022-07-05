using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using IntroSE.Kanban.Backend.BusinessLayer.BoardPackage;
using DalUser = IntroSE.Kanban.Backend.DataAccessLayer.Objects.User;


namespace IntroSE.Kanban.Backend.BusinessLayer.UserPackage
{
    internal class UserMember:User
    {

        /// <summary>
        /// Constructor for a new user member .
        /// This user is not a host and is just a member of a board.
        /// </summary>
        /// <param name="email">The new user's email</param>
        /// <param name="nick"> The new user's nickname</param>
        /// <param name="password">The new user's password</param>
        /// <param name="myBoard">The new Users existing board.</param>
        internal UserMember(string email, string nick, string password, Board myBoard) : base(email,nick,password)
        {
            this.myBoard = new BoardMember(myBoard);
            DalUser newUser = new DalUser(email.ToLower(), nick, password, this.myBoard.GetDataBaseID(), false);
            newUser.Insert();
            this.id = newUser.Id;
        }

        /// <summary>
        /// A construction that gets a Dal user and it's related BS board and creates a business user accordingly
        /// Creates a Board Member for the user Member.
        /// </summary>
        /// <param name="dluser">the Dal user </param>
        /// <param name="myBoard"> the user's board</param>
        internal UserMember(DalUser dluser, Board myBoard) :base(dluser)//converting from Dal User to business layer user.
        {           
            this.myBoard = new BoardMember(myBoard);
        }

        internal override bool GetIsCreator()
        {
            return false;
        }
    }
}
