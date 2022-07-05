using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Presentation.Model
{
    //Represents a notifiable Model Object, which references to the Classes that are in the 'Model' folder.
    public class NotifiableModelObject:NotifiableObject
    {
        public NotifiableModelObject(BackendController controller) : base(controller)
        {
          
        }
    }
}
