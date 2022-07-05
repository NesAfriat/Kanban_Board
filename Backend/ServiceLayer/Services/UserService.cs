using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using IntroSE.Kanban.Backend.BusinessLayer;
using bsUser = IntroSE.Kanban.Backend.BusinessLayer.UserPackage.User;
namespace IntroSE.Kanban.Backend.ServiceLayer
{
    class UserService
    {
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        private UserController uc;
        internal UserService()
        {
            uc = new UserController();
        }

        internal UserController GetUserController() { return uc; }

        /// <summary>
        /// a spesific function to register a user for existing board instead of creating new one
        /// </summary>
        /// <param name="email">The email address of the user</param>
        /// <param name="password">an user password - works only if is legal</param>
        /// <param name="nickname">The user name.</param>
        ///  /// <param name="emailHost">The email of the user who owns the board to join</param>
        /// <returns>A response. The response should contain a error message in case of an error</returns>
        internal Response RegisterWithExistingBoard(string email, string password, string nickname, string emailHost) {
            try
            {

                uc.RegisterWithExistingBoard(email, password, nickname, emailHost);
                log.Info("Registered to an existing board successfuly");
                return new Response();

            }
            catch(Exception e)
            {
                log.Info("Couldn't register to an existing board");
                return new Response(e.Message);
            }
        }
        /// <summary>
        /// Calls register of UserController, which creates a new user and saves it's data.
        /// </summary>
        /// <param name="email">the email of the registering user</param>
        /// <param name="password">the password of the registering user</param>
        /// <param name="nickname">the nickname of the registering user</param>
        /// <returns>Response object containing the thrown error or null if an error wasn't thrown</returns>
        internal Response Register(string email, string password, string nickname)
        {
            try
            {
                uc.Register(email, password, nickname);
                log.Info("A new user was created and registered successfuly");
                return new Response(null);
            }
            catch (Exception e)
            {
                log.Debug("Couldn't complete registration or save the new User information");
                return new Response(e.Message);
            }

        }

        /// <summary>
        /// alls UserController Login(), returns the user that was logged in. (Service layer User)
        /// </summary>
        /// <param name="email"></param>
        /// <param name="password"></param>
        /// <returns>Response object of type User which contains an error message if there 
        /// was an exception, or the user that just logged in if there wasn't an error</returns>
        internal Response<User> Login(string email, string password)
        {
            try
            {
                bsUser bsu = uc.Login(email, password);
                User user = new User(bsu.GetEmail(),bsu.GetNickname());
                log.Info("User: Emai: " + email + " has logged in successfuly");
                return new Response<User>(user);
            }
            catch (Exception e)
            {
                log.Debug("Couldn't login or save the new information");
                return new Response<User>(e.Message);
            }

        }


        /// <summary>
        /// Calls UserController Logout().
        /// </summary>
        /// <param name="email">the email of the current logged in user</param>
        /// <returns>Response that contains the error message if an error as occurd, or null if there wasn't an error</returns>
        internal Response Logout(string email)
        {
            try
            {
                uc.LogOut(email);
                log.Info("User: Email: " + email + "has logged out successfuly");
                return new Response(null);

            }
            catch (Exception e)
            {
                log.Debug("Couldn't manage to logout or save the information");
                return new Response(e.Message);
            }

        }

    }

}

