using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using IntroSE.Kanban.Backend.DataAccessLayer;
namespace IntroSE.Kanban.Backend.BusinessLayer
{
    abstract class PersistedObject<T> where T : DTO
    {
        /// <summary>
        /// each persistent object will be implementing this abstract class.
        /// </summary>
        protected long id=-1; //hold the boject's key in its' SQL table
        internal abstract T ToDalObject(); //a method to convert business obeject into DTO object
       
    }
}
