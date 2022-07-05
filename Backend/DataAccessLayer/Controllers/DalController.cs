using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using IntroSE.Kanban.Backend.DataAccessLayer.Objects;
using System.IO;
using System.Security.AccessControl;
using System.Data.SQLite;
namespace IntroSE.Kanban.Backend.DataAccessLayer
{
    internal abstract class DalController
    {
        protected readonly string _connectionString;
        protected readonly string _tableName;
        protected readonly string _IdsTableName = "sqlite_sequence";
        protected readonly string _DataBaseName = "KanbanDB.db";
        protected static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        internal DalController(string tableName)
        {
            this._tableName = tableName;
            string path =Path.GetFullPath(Path.Combine( Directory.GetCurrentDirectory(), _DataBaseName));
            this._connectionString = $"Data Source={path}; Version=3;";

            //Checking if a table exists, if not , creating one.
            if (!File.Exists(path))
            {
                SQLiteConnection.CreateFile(_DataBaseName);
                CreateTables();
            }


        }
        /// <summary>
        /// Creating  all of the tables.
        /// </summary>
        private void CreateTables()
        {
            using (var connection = new SQLiteConnection(_connectionString))
            {
                try
                {
                   
                    string createBoardTable = $"CREATE TABLE {BoardController.BoardTableName}({DTO.IDColumnName} INTEGER PRIMARY KEY AUTOINCREMENT,{Board._MaxTaskIdColumn} INTEGER)";
                    string createColumnTable = $"CREATE TABLE {ColumnController.ColumnTableName}({DTO.IDColumnName} INTEGER PRIMARY KEY AUTOINCREMENT,{Column._ColumnIdColumn} INTEGER,{Column._LimitColumn} INTEGER ,{Column._NameColumn} TEXT,{Column._BoardKeyColumn} INTEGER,FOREIGN KEY({Column._BoardKeyColumn}) REFERENCES Board({DTO.IDColumnName}))";
                    string createTasksTable = $"CREATE TABLE {TaskController.TaskTableName}({DTO.IDColumnName} INTEGER PRIMARY KEY AUTOINCREMENT,{Task._TaskIdColumn} INTEGER,{Task._TitleColumn} TEXT,{Task._DesctiptionColumn}  TEXT,{Task._CreationTimeColumn} TEXT,{Task._DueDateColumn} TEXT,{ Task._ColumnIdColumn} INTEGER,{Task._EmailAssigneeColumn} TEXT,FOREIGN KEY({Task._ColumnIdColumn}) REFERENCES Columns({DTO.IDColumnName}))";
                    string createUsersTable = $"CREATE TABLE {UserController.UserTableName}({DTO.IDColumnName} INTEGER PRIMARY KEY AUTOINCREMENT,{User._EmailColumn} TEXT,{User._NicknameColumn} TEXT,{User._PasswordColumn} TEXT,{User._IsCreatorColumn} INTEGER,{User._BoardIdColumn} INTEGER,FOREIGN KEY({User._BoardIdColumn}) REFERENCES Board({DTO.IDColumnName}))";

                    string[] createTablesByOrder = { createUsersTable, createBoardTable, createColumnTable, createTasksTable };
                    string[] TablesNames = { UserController.UserTableName, BoardController.BoardTableName , ColumnController.ColumnTableName, TaskController.TaskTableName };
                    connection.Open();
                    for (int i = 0; i < createTablesByOrder.Length; i++)
                    {

                        SQLiteCommand command = new SQLiteCommand(createTablesByOrder[i], connection);
                        command.ExecuteNonQuery();       
                        //Creating a dummy row for the sql_sequecne table to start counting.
                        SQLiteCommand command1 = new SQLiteCommand("Insert Into "+TablesNames[i]+" DEFAULT VALUES", connection);
                        command1.ExecuteNonQuery();
                        SQLiteCommand command2 = new SQLiteCommand("DELETE FROM " + TablesNames[i], connection);
                        command2.ExecuteNonQuery();

                    }

                }
                catch (Exception e)
                {
                    throw new Exception(e.Message);
                }
                finally
                {
                    connection.Close();
                    
                }
            }

        }

        //An abstract method which gets a dto obj and the applying class will implement the method by inserting the obj into the correct table.
        internal abstract bool Insert(DTO obj);

        //Getting the max AutoIncrement ID that is the primary key of every table, from the sql_sequence table.
        public long GetMaxId()
        {
            long id = -1;
            using (var connection = new SQLiteConnection(_connectionString))
            {
                SQLiteCommand command = new SQLiteCommand(null, connection);
                command.CommandText = $"SELECT seq FROM {_IdsTableName} WHERE name=@tName;";
                SQLiteDataReader dataReader = null;
                try
                {
                    SQLiteParameter tName = new SQLiteParameter("@tName", _tableName);
                    command.Parameters.Add(tName);
                    connection.Open();
                    dataReader = command.ExecuteReader();

                    if (dataReader.Read())
                        id = (long)dataReader["seq"];
                }
                finally
                {
                    if (dataReader != null)
                    {
                        dataReader.Close();
                    }

                    command.Dispose();
                    connection.Close();
                }

            }
            if (id == -1)
                throw new Exception("Couldnt find the largest ID in from the DataBase on table: " + _tableName);
            return id;

        }

        /// <summary>
        /// Updates the attribute required with a new value in the data base.
        /// </summary>
        /// <param name="id">The unique id of the row which we want to update</param>
        /// <param name="attributeName">The column name of which we want to update</param>
        /// <param name="attributeValue">The new value (string) to insert at the specific column.</param>
        /// <returns> true if an update of some rows occurd, false elseway</returns>
        public bool Update(long id, string attributeName, string attributeValue)
        {
            int res = -1;
            using (var connection = new SQLiteConnection(_connectionString))
            {
                SQLiteCommand command = new SQLiteCommand
                {
                    Connection = connection,
                    CommandText = $"update {_tableName} set [{attributeName}]=@{attributeName} where Id={id}"
                };
                try
                {

                    command.Parameters.Add(new SQLiteParameter(attributeName, attributeValue));
                    connection.Open();
                    res = command.ExecuteNonQuery();
                }
                catch(Exception e)
                {
                    log.Error("Couldn't update table: "+_tableName);
                    throw new Exception(e.Message);
                }
                finally
                {
                    command.Dispose();
                    connection.Close();
                }

            }
            if (res <=0)
                log.Error("res = -1 --> Did not change the required fields in the DataBase");
            return res > 0;
            
        }

        /// <summary>
        /// Updates the attribute required with a new value in the data base.
        /// </summary>
        /// <param name="id">The unique id of the row which we want to update</param>
        /// <param name="attributeName">The column name of which we want to update</param>
        /// <param name="attributeValue">The new value (long) to insert at the specific column.</param>
        /// <returns> true if an update of some rows occurd, false elseway</returns>
        public bool Update(long id, string attributeName, long attributeValue)
        {
            int res = -1;
            using (var connection = new SQLiteConnection(_connectionString))
            {
                SQLiteCommand command = new SQLiteCommand();

                command.Connection = connection;
                command.CommandText = $"UPDATE {_tableName} SET {attributeName}=@{attributeName} where Id={id}";
                
                try
                {

                    command.Parameters.Add(new SQLiteParameter(attributeName, attributeValue));
                    connection.Open();
                    res = command.ExecuteNonQuery();
                }
                catch (Exception e)
                {
                    log.Error("Couldn't update table: " + _tableName);
                    throw new Exception(e.Message);
                }
                finally
                {
                    command.Dispose();
                    connection.Close();
                }

            }
            if (res <= 0)
                log.Error("res = -1 --> Did not change the required fields in the DataBase");
            return res > 0;

        }

        /// <summary>
        /// Selects all of the information from the table naem that the current controller holds.
        /// </summary>
        /// <returns> a list of the objects (as DTO) from were at the table</returns>
        internal List<DTO> Select()
        {
            List<DTO> DTOList = new List<DTO>();
            using (var connection = new SQLiteConnection(_connectionString))
            {
                SQLiteCommand command = new SQLiteCommand(null, connection);
                command.CommandText = $"select * from {_tableName};";
                SQLiteDataReader dataReader = null;
                try
                {
                    connection.Open();
                    dataReader = command.ExecuteReader();

                    while (dataReader.Read())
                    {
                        DTOList.Add(ConvertReaderToObject(dataReader));

                    }
                }
                catch(Exception e)
                {
                    throw new Exception(e.Message);
                }
                finally
                {
                    if (dataReader != null)
                    {
                        dataReader.Close();
                    }

                    command.Dispose();
                    connection.Close();
                }

            }
            return DTOList;
        }

        //Each class that implements this gets a reader with the corresond table to it's type, and converting the data that on the reader to a DTO objects with the appropriate values.
        protected abstract DTO ConvertReaderToObject(SQLiteDataReader reader);

        //Deletes all the data from the table name that this instance holds.
        internal void DeleteAllData()
        {
            using (var connection = new SQLiteConnection(_connectionString))
            {
                var command = new SQLiteCommand
                {
                    Connection = connection,
                    CommandText = $"delete from {_tableName}"
                };
                try
                {
                    connection.Open();
                    command.ExecuteNonQuery();
                }
                finally
                {
                    command.Dispose();
                    connection.Close();
                }

            }
        }

        /// <summary>
        /// Deleting the object from the table name that this instance hold
        /// </summary>
        /// <param name="DTOObj">The DTO objects that we want to delete from the data base</param>
        /// <returns>  true if the object was deleted, false elseway</returns>
        public bool Delete(DTO DTOObj)
        {
            int res = -1;

            using (var connection = new SQLiteConnection(_connectionString))
            {
                var command = new SQLiteCommand
                {
                    Connection = connection,
                    CommandText = $"delete from {_tableName} where id={DTOObj.Id}"
                };
                try
                {
                    connection.Open();
                    res = command.ExecuteNonQuery();
                }
                finally
                {
                    command.Dispose();
                    connection.Close();
                }

            }
            if (res <=0)
                log.Error("res = -1 --> Did not change the required fields in the DataBase");
            return res > 0;
        }
       
    }
}
