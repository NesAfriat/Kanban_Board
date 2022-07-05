using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IntroSE.Kanban.Backend.DataAccessLayer
{
    internal abstract class DTO
    {
        /// <summary>
        ///an abstarct class for all the DTO objects, gets a specific controller for a table of each object and activates his functions.
        /// </summary</param>
        /// <param name="controller">a specific controller for a table in the SQL that holds the object of specific DTO object</param>
        /// <returns></returns>
        internal const string IDColumnName = "Id";
        public long Id { get; set; } = -1;
        protected DalController controller;
        internal DTO(DalController controller)
        {
            this.controller = controller;
        }

        //Calling the controller for the following actions with the current object's id.



        internal void Insert()
        {
            if(Id!=-1)
                controller.Insert(this);
        }

        internal void Delete()
        {
            if(Id!=-1)
                controller.Delete(this);
        }

        internal void Update(string attName, string attValue)
        {
            if(Id!=-1)
                controller.Update(Id, attName, attValue);
        }

        internal void Update(string attName, long attValue)
        {
            if(Id!=-1)
                controller.Update(Id, attName, attValue);
        }


        internal long GetMaxId()
        {
            return controller.GetMaxId();
        }

        
    }
}
