using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IntroSE.Kanban.Backend.DataAccessLayer.Objects
{
    class Column : DTO
    {
        private long ColumnId;
        private string Name;
        private long Limit;
        private long BoardId;

        //Setters that update the fields in the data base.
        internal long columnId { get => ColumnId; set { Update(_ColumnIdColumn, value); } }
        internal string name { get => Name; set { Update(_NameColumn, value); } }
        internal long limit { get => Limit; set { Update(_LimitColumn, value); } }
        internal long boardId { get => BoardId; }

        //the columns names in the data base.
        internal const string _ColumnIdColumn = "ColumnID";
        internal const string _LimitColumn = "ColumnLimit";
        internal const string _NameColumn = "Name";
        internal const string _BoardKeyColumn = "BoardID";
        /// <summary>
        ///a DTO class for columns, holds all the businesss cloumn fields, a controller(in his parent) to manage access to the table and and keys.
        /// </summary>
        /// <param name="ColumnId">holds the key of the column in the columns' table</param>
        /// <param name="BordID">holds the key of the matching board in the Boards' table</param>
        /// <returns></returns>
        /// 

        //A constructor for a new task, Generating id from the data base.
        public Column(long colId, string name, long limit,long boardId):base (new ColumnController())
        {
            this.Id = GetMaxId()+1;
            this.ColumnId = colId;
            this.Name = name;
            this.Limit = limit;
            this.BoardId = boardId;
        }

        //A constructor for an existing task, receiving an id.
        public Column(long id,long columnId, string name, long limit, long boardId) : base(new ColumnController())
        {
            this.Id = id;
            this.ColumnId = columnId;
            this.Name = name;
            this.Limit = limit;
            this.BoardId = boardId;
        }

        //A constructor for updating a column. Only the id needed.
        public Column(long id) : base(new ColumnController())
        {
            this.Id = id;
        }
        


    }
}
