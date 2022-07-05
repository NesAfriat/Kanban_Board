using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data.SQLite;
using IntroSE.Kanban.Backend.DataAccessLayer.Objects;

namespace IntroSE.Kanban.Backend.DataAccessLayer
{
    class TaskController:DalController
    {
        internal const string TaskTableName = "Tasks";

        public TaskController() : base(TaskTableName) { }
        protected override DTO ConvertReaderToObject(SQLiteDataReader reader)
        {
            string description;
            if (reader.IsDBNull(3))
                description = "";
            else
                description = (string)reader[Task._DesctiptionColumn];
            

            Task result = new Task((long)reader[Task._TaskIdColumn], (long)reader[DTO.IDColumnName], (string)reader[Task._TitleColumn], description, DateTime.Parse((string)reader[Task._CreationTimeColumn]), DateTime.Parse((string)reader[Task._DueDateColumn]),(long)reader[Task._ColumnIdColumn],(string)reader[Task._EmailAssigneeColumn]);
            return result;
        }

        //Converting the output of Select() to a list of tasks.s
        internal  List<Task> SelectAllColumns()
        {
            List<Task> tasks = Select().Cast<Task>().ToList();
            return tasks;
        }
        internal override bool Insert(DTO task1)
        {
            Task task = (Task)task1;
            using (var connection = new SQLiteConnection(_connectionString))
            {
                int res = -1;
                SQLiteCommand command = new SQLiteCommand(null, connection);
                try
                {
                    connection.Open();
                    command.CommandText = $"INSERT INTO {TaskTableName} ({Task._TaskIdColumn},{Task._TitleColumn},{Task._DesctiptionColumn},{Task._CreationTimeColumn},{Task._DueDateColumn},{Task._ColumnIdColumn},{Task._EmailAssigneeColumn}) " +
                        $"VALUES (@TaskId,@Title,@Description,@Creation,@DueDate,@cId,@emailAs);";

                    SQLiteParameter taskId = new SQLiteParameter(@"TaskId", task.taskId);
                    SQLiteParameter title = new SQLiteParameter(@"Title", task.title);
                    SQLiteParameter description= new SQLiteParameter(@"Description",task.description);
                    SQLiteParameter creationTime = new SQLiteParameter(@"Creation", task.creationTime.ToString());
                    SQLiteParameter dueDate = new SQLiteParameter(@"DueDate", task.dueDate.ToString());
                    SQLiteParameter columnId = new SQLiteParameter(@"cId", task.columnId);
                    SQLiteParameter emailAs = new SQLiteParameter(@"emailAs", task.emailAssignee);

                    command.Parameters.Add(taskId);
                    command.Parameters.Add(title);
                    command.Parameters.Add(description);
                    command.Parameters.Add(creationTime);
                    command.Parameters.Add(dueDate);
                    command.Parameters.Add(columnId);
                    command.Parameters.Add(emailAs);
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
