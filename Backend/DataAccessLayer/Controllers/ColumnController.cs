using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using IntroSE.Kanban.Backend.DataAccessLayer.Objects;

namespace IntroSE.Kanban.Backend.DataAccessLayer
{
    class ColumnController : DalController
    {
        internal const string ColumnTableName = "Columns";
        public ColumnController() : base(ColumnTableName) { }

        protected override DTO ConvertReaderToObject(SQLiteDataReader reader)
        {
            Column result = new Column((long)reader[DTO.IDColumnName],(long)reader[Column._ColumnIdColumn], (string)reader[Column._NameColumn], (long)reader[Column._LimitColumn], (long)reader[Column._BoardKeyColumn]);
            return result;
        }

        //Converting the output of select to a list of columns.
        internal List<Column> SelectAllColumns()
        {
            List<Column> Columns = Select().Cast<Column>().ToList();
           
            return Columns;
        }

        internal void UpdateColumnsId(Dictionary<long, long> columns)
        {
            using (var connection = new SQLiteConnection(_connectionString))
            {
                try {
                    connection.Open();
                    foreach (KeyValuePair<long, long> c in columns) {
                        SQLiteCommand command = new SQLiteCommand();
                        long id = c.Key;
                        long colId = c.Value;
                        command.Connection = connection;
                        command.CommandText = $"UPDATE {_tableName} SET {Column._ColumnIdColumn}=@{Column._ColumnIdColumn} where Id={id}";
                        command.Parameters.Add(new SQLiteParameter(Column._ColumnIdColumn, colId));
                        command.ExecuteNonQuery();
                    }
                }
                finally
                {
                    connection.Close();
                }
            }

        }

        internal override bool Insert(DTO column1)
        {
            Column column = (Column)column1;
            using (var connection = new SQLiteConnection(_connectionString))
            {
                int res = -1;
                SQLiteCommand command = new SQLiteCommand(null, connection);
                try
                {
                    connection.Open();
                    command.CommandText = $"INSERT INTO {ColumnTableName} ({Column._ColumnIdColumn},{Column._NameColumn},{Column._LimitColumn},{Column._BoardKeyColumn}) " +
                        $"VALUES (@ColumnId,@Name,@Limit,@BoardKey);";

                    SQLiteParameter ColumnId = new SQLiteParameter(@"ColumnId", column.columnId);
                    SQLiteParameter name = new SQLiteParameter(@"Name", column.name);
                    SQLiteParameter limit = new SQLiteParameter(@"Limit", column.limit);
                    SQLiteParameter boardKey = new SQLiteParameter(@"BoardKey", column.boardId);

                    command.Parameters.Add(ColumnId);
                    command.Parameters.Add(name);
                    command.Parameters.Add(limit);
                    command.Parameters.Add(boardKey);
                    command.Prepare();

                    res = command.ExecuteNonQuery();
                }
                catch (Exception e)
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
    }
}
