using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SQLite;
using IntroSE.Kanban.Backend.DataAccessLayer.Objects;

namespace IntroSE.Kanban.Backend.DataAccessLayer
{
    class BoardController:DalController
    {
        internal const string BoardTableName = "Board";
        public BoardController() : base(BoardTableName)
        {
        }
        //Converting the output of select to a list of boards.
        internal List<Board> SelectAllColumns()
        {
            List<Board> boardList = Select().Cast<Board>().ToList();
            
            return boardList;
        }
        internal override bool Insert(DTO board1)
        {
            Board board = (Board)board1;
            using (var connection = new SQLiteConnection(_connectionString))
            {
                int res = -1;
                SQLiteCommand command = new SQLiteCommand(null, connection);
                try
                {
                    connection.Open();
                    command.CommandText = $"INSERT INTO {BoardTableName} ({Board._MaxTaskIdColumn}) " +
                        $"VALUES (@maxIdVal);";
                
                    SQLiteParameter titleParam = new SQLiteParameter(@"maxIdVal", board.maxIdCounter);

                    command.Parameters.Add(titleParam);
                    command.Prepare();

                    res = command.ExecuteNonQuery();
                }
                catch(Exception e)
                {                   
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


        protected override DTO ConvertReaderToObject(SQLiteDataReader reader)
        {
            Board result = new Board((long)reader[DTO.IDColumnName], (long)reader[Board._MaxTaskIdColumn]);
            return result;
        }

    }
}
