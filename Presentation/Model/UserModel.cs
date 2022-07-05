using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Presentation.Model
{
     public class UserModel:NotifiableModelObject
    {
       
        //Constructor for a new UserModel
        public UserModel(BackendController bc,string email,string nickname,BoardModel board):base(bc)
        {
            this.email = email;
            this.board = board;
            this.Nickname = nickname;
            isCreator = board.HostEmail == email;
        }

        /// <summary>
        /// Adds a new task to the board of the user.
        /// </summary>
        /// <param name="task">The task to add</param>
        public void AddTask(TaskModel task)
        {
            board.addTask(task);
        }

        //Represents if the user is the creator of it's board.
        private bool isCreator;
        public bool IsCreator { get => isCreator; }

        //The user's email.
        private string email;
        public string Email
        {
            get => email;
            set { email = value;
                RaisePropertyChanged("Email");
            }
        }


        //The board of the user.
        private BoardModel board;
        public BoardModel Board { get => board; }


        //The user's nickname.
        private string nickname;
        public string Nickname { get => nickname;  set { nickname = value; RaisePropertyChanged("Nickname"); } }
       
    }
}
