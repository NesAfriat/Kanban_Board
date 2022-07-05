using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace IntroSE.Kanban.Backend.DataAccessLayer.Objects
{
    class User:DTO
    {
        private string Email;
        private string Nickname;
        private string Password;
        private long BoardId;
        private bool IsCreator;
        //Setter for the fields that update them in the data base.
        internal string email { get => Email; set { Update(_EmailColumn, value); } }
        internal string nickname { get => Nickname; set { Update(_NicknameColumn, value); } }
        internal string password { get => Password; set { Update(_PasswordColumn, value); } }
        internal long boardId { get => BoardId; set { Update(_BoardIdColumn, value); } }
        internal bool isCreator { get => IsCreator; }
        //The columns names in the users table.
        internal const string _EmailColumn = "Email";
        internal const string _NicknameColumn= "Nickname";
        internal const string _PasswordColumn = "Password";
        internal const string _BoardIdColumn = "BoardId";
        internal const string _IsCreatorColumn = "IsCreator";
        /// <summary>
        ///a DTO class for users, holds all the businesss' users fields, getters and setters, a controller(in his parent) to manage access to the table and and a key.
        /// </summary></param>
        /// <returns></returns>

        // A consturcot for a new user 
        public User(string email,string nickname,string password,long boardId, bool IsCreator):base(new UserController())
        {
            this.Id = GetMaxId()+1;
            this.Email = email;
            this.Nickname = nickname;
            this.Password = password;
            this.BoardId = boardId;
            this.IsCreator = IsCreator;
        }

        //A constructor for an existing user, getting it's id.
        public User(long id, string email, string nickname, string password, long boardId, bool IsCreator) : base(new UserController())
        {
            this.Id = id;
            this.Email = email;
            this.Nickname = nickname;
            this.Password = password;
            this.BoardId = boardId;
            this.IsCreator = IsCreator;
        }
        // a setter fo updating the user with the id entered.
        public User(long id) :base(new UserController())
        {
            this.Id = id;
        }

       
    }
   
}
