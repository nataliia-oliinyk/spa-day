db = db.getSiblingDB("test");

var collectionTreatmentsExists = database.ListCollectionNames().ToList().Contains("treatments");
if (collectionTreatmentsExists == false) {
db.createCollection('treatments', { capped: false });
db.treatments.insertMany([
    { "name": "massage", "description": "Restore your sense of peace and ease with a relaxing massage.", "image":"massage.jpg","durationInMinutes": 60, },
    { "name": "facial", "description": "Give your face a healthy glow with this cleansing treatment.", "image":"facial.jpg","durationInMinutes": 30, },
    { "name": "scrub", "description": "Invigorate your body and spirit with a scented Himalayan salt scrub.", "image":"scrub.jpg","durationInMinutes": 25, },
]);
}
var collectionEmployeesExists = database.ListCollectionNames().ToList().Contains("employees");
if (collectionEmployeesExists == false) {
db.createCollection('employees', { capped: false });
db.employees.insertMany([
    { "name": "Divya", "treatments":  ["facial", "scrub"], "image":"divya.jpg" },
    { "name": "Sandra", "treatments":  ["facial", "massage"], "image":"sandra.jpg" },
    { "name": "Michael", "treatments":   ["facial", "scrub", "massage"], "image":"michael.jpg" },
    { "name": "Mateo", "treatments":   ["massage"], "image":"mateo.jpg" },
   ]);
}


