db = db.getSiblingDB('TestDiary');

db.createCollection('teachers');
db.createCollection('students');
db.createCollection('subjects');

db.teachers.insertMany([
    {
        "_id": "6654f21fb7d8890cee94313e",
        "name": "teacherTest1",
        "email": "teacherTest1",
        "subjects": [
            "6658f64f998d6a047e82a3a4",
            "6654f2acb7d8890cee94313f"
        ],
        "_class": "org.example.newmongoproject.model.Teacher"
    }
]);

db.students.insertMany([
    {
        "_id": "6653a40280aa77729c0c70f8",
        "name": "Pasha",
        "email": "kii@gmail.com",
        "_class": "org.example.newmongoproject.model.Student"
    },
    {
        "_id": "6654f1a9b7d8890cee94313d",
        "name": "TestStudent1",
        "email": "test1@gmail.com",
        "_class": "org.example.newmongoproject.model.Student"
    },
    {
        "_id": "665745dd247729488e58f83e",
        "name": "testStudent2",
        "email": "test2@gmail.com",
        "_class": "org.example.newmongoproject.model.Student"
    }
]);

db.subjects.insertMany([
    {
        "_id": "6654f2acb7d8890cee94313f",
        "name": "Math",
        "teacherId": "6654f21fb7d8890cee94313e",
        "gradesStudents": {
            "6653a40280aa77729c0c70f8": [2, 3, 4, 5],
            "6654f1a9b7d8890cee94313d": [5, 5, 5, 5, 5],
            "665745dd247729488e58f83e": [2, 3, 4, 5, 3]
        },
        "_class": "org.example.newmongoproject.model.Subject"
    }
]);