using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IntroSE.Kanban.Backend.DataAccessLayer.Objects
{
    class Board:DTO
    {
        private long MaxIdCounter;
        //A setter for the max id counter field in the data base.
        internal long maxIdCounter { get => MaxIdCounter; set { Update(_MaxTaskIdColumn, value); } }
        internal const string _MaxTaskIdColumn = "TasksIdCounter";

       //A constructor for an existing board
        public Board (long id, long maxIdCounter) :base(new BoardController())
       {
            this.Id = id;
            this.MaxIdCounter = maxIdCounter;
        }

        //A constructor for a new Board, generating the id from the data base.
        public Board(long maxIdCounter) : base(new BoardController())
        {
            this.Id = GetMaxId()+1;
            this.MaxIdCounter = maxIdCounter;
        }

        //A constructor for update.
        public Board(long id,bool getBoardDal) : base(new BoardController())
        {
            this.Id = id;
        }
        /// <summary>
        /// A dal class for the Board object, gets the data of the dal type columns and the max id
        /// of tasks for each board - as counter for its' id's
        /// </summary>


    }
}
