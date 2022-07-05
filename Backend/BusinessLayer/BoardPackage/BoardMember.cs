using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IntroSE.Kanban.Backend.BusinessLayer.BoardPackage
{
    internal class BoardMember:Board
    {
        private string errMsg = "This user doesnt have permissions to change the columns";
        public BoardMember(Board board) :base(board)
        {

        }
        // does not have premission to change the index of a coulmn.
        public override void MoveColumnRight(int cOrdinal)
        {
            throw new Exception(errMsg);
        }
        // does not have premission to change the index of a coulmn.
        public override void MoveColumnLeft(int cOrdinal)
        {
            throw new Exception(errMsg);

        }
        // does not have premission to change the limit.
        internal override void SetLimit(int cOrdinal,int limit) {

            throw new Exception(errMsg);

        }
        // does not have premission to change the name of a coulmn.
        internal override void ChangeColumnName(int columnOrdinal, string newName)
        {
            throw new Exception(errMsg);

        }

        //does not have permission to change remove a column
        public override void RemoveColumn(int columnOrdinal)
        {
            throw new Exception(errMsg);

        }

        public override void AddColumn(int cOrdianl, string name)
        {
            throw new Exception(errMsg);
        }


    }
}
