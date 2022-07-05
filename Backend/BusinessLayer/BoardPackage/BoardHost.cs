using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DlBoard = IntroSE.Kanban.Backend.DataAccessLayer.Objects.Board;

namespace IntroSE.Kanban.Backend.BusinessLayer.BoardPackage
{
    class BoardHost:Board
    {
        //Constructors just like in the board:


        public BoardHost(string email) : base(email)
        {

        }

        public BoardHost(Board other) : base(other)
        {
            
            
        }

        //Database constructor that converts from the dal objects to business object.
        public BoardHost(DlBoard dlb, Dictionary<int, Column> businessColumnsOfBoard) : base(dlb, businessColumnsOfBoard) { }


    }
}
