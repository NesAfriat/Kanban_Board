using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel;
namespace Presentation
{
    //Represent an object that has binding to it , and can notify on changes.
    public class NotifiableObject:INotifyPropertyChanged
    {
        //The backend controller for the changes in the service.
        public BackendController backendController;

        public event PropertyChangedEventHandler PropertyChanged;

        //Notifies on changes to the property in the argument.
        protected void RaisePropertyChanged(string property)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(property));
        }

        //Constructor.
        public NotifiableObject(BackendController bc)
        {
            this.backendController = bc;
        }
    }
}
