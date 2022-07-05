using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SQLite;
using IntroSE.Kanban.Backend.DataAccessLayer.Objects;

namespace IntroSE.Kanban.Backend.DataAccessLayer
{
    class UserController:DalController
    {
        internal const string UserTableName = "Users";

        public UserController() : base(UserTableName) { }
        protected override DTO ConvertReaderToObject(SQLiteDataReader reader)
        {
            long id = (long)reader[DTO.IDColumnName];
            string email = (string)reader[User._EmailColumn];
            string nick = (string)reader[User._NicknameColumn];
            string pass = (string)reader[User._PasswordColumn];
            long boardId = (long)reader[User._BoardIdColumn];
            bool isCreator= (int)((long)reader[User._IsCreatorColumn]) !=0;
            User result = new User(id,email,nick,pass,boardId,isCreator);
            return result;
        }

        //Converting the output of Select() to a list of Users.
        internal  List<User> SelectAllColumns()
        {
            List<User> users = Select().Cast<User>().ToList();
           
            return users;
        }
        
        
        internal override bool Insert(DTO user1)
        {
            
            User user = (User)user1;
            using (var connection = new SQLiteConnection(_connectionString))
            {
                int res = -1;
                SQLiteCommand command = new SQLiteCommand(null, connection);
                try
                {
                        
                    connection.Open();
                    command.CommandText = $"INSERT INTO {UserTableName} ({User._EmailColumn},{User._NicknameColumn},{User._PasswordColumn},{User._BoardIdColumn},{User._IsCreatorColumn}) " +
                        $"VALUES (@Email,@nick,@pass,@boardId,@IsCreator);";

                    SQLiteParameter email = new SQLiteParameter(@"Email", user.email);
                    SQLiteParameter nick = new SQLiteParameter(@"nick", user.nickname);
                    SQLiteParameter pass = new SQLiteParameter(@"pass", user.password);
                    SQLiteParameter boardId = new SQLiteParameter(@"boardId", user.boardId);
                    SQLiteParameter IsCreator = new SQLiteParameter(@"IsCreator", user.isCreator);


                    command.Parameters.Add(email);
                    command.Parameters.Add(nick);
                    command.Parameters.Add(pass);
                    command.Parameters.Add(boardId);
                    command.Parameters.Add(IsCreator);
                    command.Prepare();

                    res = command.ExecuteNonQuery();
                }
                catch (Exception e)
                {
                    log.Error("Couldn't insert new User : " + e.Message);
                    throw new Exception(e.Message);
                }
                finally
                {
                    command.Dispose();
                    connection.Close();
                }
                return res > 0;
            }
        }
    }
}
