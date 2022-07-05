using System;
using NUnit.Framework;
using Moq;
using IntroSE.Kanban.Backend.ServiceLayer;
using Column = IntroSE.Kanban.Backend.BusinessLayer.Column;
using Task = IntroSE.Kanban.Backend.BusinessLayer.Task;
using dlTask = IntroSE.Kanban.Backend.DataAccessLayer.Objects.Task;

namespace UnitTestKanban
{
  
    public class ColumnsTests
    {
        Column col1;
        Mock<Task> task1;
        Mock<Task> task2;
        Mock<dlTask> dltask;
        Mock<dlTask> dltask2;
        public ColumnsTests()
        {
        }

        [SetUp]
        public void SetUp()
        {
            task1 = new Mock<Task>(1, DateTime.MaxValue, "task1", "description", DateTime.MaxValue, 1, "Nes");
            dltask = new Mock<dlTask>(1, 1, "task1", "description", DateTime.MaxValue, DateTime.MaxValue, 1, "Nes");
            task1.Setup(m => m.ToDalObject()).Returns(dltask.Object);
            task2 = new Mock<Task>(2, DateTime.MaxValue, "task2", "description", DateTime.MaxValue, 1, "Nes");
            dltask2 = new Mock<dlTask>(2, 2, "task2", "description", DateTime.MaxValue, DateTime.MaxValue, 1, "Nes");
            task2.Setup(m => m.ToDalObject()).Returns(dltask2.Object);
            col1 = new Column("column1", 100, 0, 1);
            task1.Setup(m => m.GetTaskId()).Returns(1);
            task2.Setup(m => m.GetTaskId()).Returns(2);
        }

        [TearDown]
        public void TearDown()
        {
            task1.Reset();
            task2.Reset();
            dltask.Reset();
            dltask2.Reset();      
        }


        //////////////////////////////////////////////////////////////
        /// <summary>
        /// SetLimit tests
        /// Changing the limit of the column.
        /// One of the main method that allows or disallows to enter new tasks,
        /// may cause many problems if this went wrong.
        /// </summary>
        /// 

        [TestCase(-1)]
        [TestCase(0)]
        [TestCase(1)]
        public void ChangeLimitFail(int lim) //checks if limit can be set lower then the tasks' amount
        {
            //Arrange
            col1.AddTask(task1.Object);
            col1.AddTask(task2.Object);
            //Actions
            try
            {
                col1.SetLimit(lim);
                //Assert
                Assert.Fail("cant change limit lower then tasks amount");
            }
            catch (Exception)
            {
            }
        }

        [TestCase(5)]
        public void ChangeLimitSuccess(int lim) //checks if limit can be set lower then the tasks' amount
        {
            
            //Actions
            try
            {
                col1.SetLimit(lim);
                Assert.AreEqual(5, col1.GetLimit(),"New limit wasn't changed");
            }
            catch (Exception)
            { //Assert
                Assert.Fail("Should change limit successfuly");
            }
        }

        [TestCase(2)]
        public void ChangeLimitSuccessWithTasks(int lim) //checks if limit can be set lower then the tasks' amount
        {
            //Arrange
            col1.AddTask(task1.Object);
            col1.AddTask(task2.Object);
            //Actions
            try
            {
                col1.SetLimit(lim);
                Assert.AreEqual(2, col1.GetLimit(),"limit wasn't changed");
            }
            catch (Exception)
            {
                //Assert
                Assert.Fail("cant change limit lower then tasks amount");
            }
        }



        //////////////////////////////////////////////////////////////
        /// <summary>
        /// Add Tasks Tests
        /// The main important method of the project that allows the
        /// adding of new tasks.
        /// </summary>
        /// <param name="c"></param>


        [Test]
        public void IsConatainsTask() //checks if the task exist in the collection of tasks in the column
        {
            //Arrange
            col1.GetTasks().Clear();
            //Actions
            col1.AddTask(task1.Object);
            //Assert
            Assert.Contains(task1.Object, col1.GetTasks().Values);
        }

        [Test]
        public void AddTaskTestFineLimit() //checks if new Tasks can be added to a column with legal limit
        {
            //Arrange
            col1.SetLimit(4);
            //Actions
            col1.AddTask(task1.Object);
            col1.AddTask(task2.Object);
            //Assert
            Assert.IsNotEmpty(col1.GetTasks(), "suppose to contain tasks");
            Assert.AreEqual(2, col1.GetTasks().Count, "The amount of tasks suppose to be 2");
            Assert.Contains(task1.Object, col1.GetTasks().Values,"Task 1 wasn't added");
            Assert.Contains(task2.Object, col1.GetTasks().Values,"Task 2 wasn't added");
        }

       
        [Test]
        public void AddTaskTestFullColumn() //checks if new Tasks can be added to a full column
        {
            //Arrange
            col1.SetLimit(1);
            col1.AddTask(task1.Object);
            //Actions
            try
            {
                col1.AddTask(task2.Object);
                //Assert
                Assert.Fail("The Column is Full- Cant add Tasks");
            }
            catch (Exception)
            {
            }
        }
       
        [Test]
        public void AddNullTask() //checks if a null task can be added to a column
        {
            //Arrange
            Task nullTask = null; 
            //Actions
            try
            {
                col1.AddTask(nullTask);
                //Assert
                Assert.Fail("The Column is Full- Cant add Tasks");
            }
            catch (Exception)
            {
            }
        }
        [Test]
        public void AddTasksWithSameID() //checks if 2 different tasks with the same taskID can be added to the column
        {
            //Arrange
            task1.Setup(m => m.GetTaskId()).Returns(1);
            task2.Setup(m => m.GetTaskId()).Returns(1);
            col1.SetLimit(3);
            //Actions
            try
            {
                col1.AddTask(task1.Object);
                col1.AddTask(task2.Object);
                //Assert
                Assert.Fail("not suppose to add two tasks with the same ID");
            }
            catch (Exception e)
            {
                
            }
        }


        //////////////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// NameCheck Tests
        /// 
        /// The main method that checks the logic behind the 
        /// column ChangeName method, and column creation.
        /// </summary>
        /// <param name="c"></param>
        /// 

        [TestCase("column1")]
        [TestCase("CCCC")]
        [TestCase("12 34")]
        [TestCase("123456789101112")]//15 characters
        public void NameCheckSuccess(string name)
        {
            try
            {
                col1.NameCheck(name);
            }
            catch 
            {
                Assert.Fail("Name should have been ok for the column");
            }
        }
        [TestCase("")]
        [TestCase("    ")]
        [TestCase("1234567891011121")]//16 characters
        [TestCase(null)]
        public void NameCheckFail(string name)
        {
            try
            {
                col1.NameCheck(name);
                Assert.Fail("Name was not supposed to be able to change");

            }
            catch
            {
            }
        }
       
     
    }

    }

