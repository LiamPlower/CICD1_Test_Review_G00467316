# CICD-1 Coding Test — Review, Improve and Reflect

**Student ID: G00467316**  
**Examination group:** A (Room Booking / JPQL)  
**Original examination pull-request URL (`exam-work`): https://github.com/LiamPlower/467316 **  
**New review repository URL: https://github.com/LiamPlower/CICD1_Test_Review_G00467316 **  

## 1. My original examination self-assessment

Review the code **as submitted in the original examination**. Do not change the original repository, branch or pull request. Check your own Group A or Group B examination paper and award marks only for evidence present in the original submission.

### Task 1 — /40
**My mark: 12**  
**Evidence from the original code (files, methods, working or missing requirements): I created a Booking model and a dto called CreateBookingRequest. I stored the organiser name, attendee count, booking status and associated room in the Booking model alongside looking for the booking/room ID.
I did not have new bookings start with status ACTIVE, and I had no validation that the organiser name was 2 to 60 characters and not blank. 
I also did not have the attendee count as a positive whole number. I felt like I didn't understand that I needed two different DTOs. I also feel as if I started with the APIs responses containing all the information but not fully sure.
`private String Count;` and `private String status;` were Strings, so the count was not a whole number and the status was not limited to valid values.

private String organiserName;

private String Count;

private String status;

private String roomName;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking; **  

### Task 2 — /40
**My mark: 8**  
**Evidence from the original code: I never used PostMapping to create or persist a booking, I know how to use it just don't know why I did not start with it at the very beginning and used a load of GETs instead. I feel as if I understood the retrieving bookings as I had started it but did struggle to finish them and to get the returns of them, 
after class I then did research and found out I forgot to add to my pom.xml file at the beginning to allow them to work properly. Finally then I did not move onto the missing rooms or bookings or when bookings exceeded room capacity as I was running out of time.

   @GetMapping("/Id")
   public List<Booking> getId(@PathVariable Long id){
   return bookingService.getClass();
   }

    @GetMapping("/Count")
    public List<Booking> getByCount(@RequestParam("count")  int count){
        return bookingService.create(count, BookingService);
    }**  

### Task 3 — /20
**My mark: 1**  
**Evidence from the original code: Got the local host working around the middle of the exam but then it failed on me.
I started ordering bookings by attendee count, highest first, but removed it because I felt I might be going wrong and was running out of time. I should have kept what I had.**

**Total self-assessed original examination mark: 21 /100**

**Checkpoint:** Commit this completed self-assessment as the **first review commit in your new repository**, before you modify or add application code. The lecturer decides the official original examination mark.

## 2. Corrections and improvements in my new project

Start from a **fresh copy of the starter template for your own exam group**, in a **new GitHub repository**. You may consult or selectively reuse your own original examination code, but the new project must demonstrate that you understand and can correct it. Do not change your original examination submission.

Describe **two or three important problems** in your original code. For each, explain what was wrong, which class or method you changed in the new project, and why the corrected approach works.

**Problem and correction 1: First problem in my original project was I used annotations such as  
`@NotBlank` and `@Positive`, but the correct dependency was missing from `pom.xml`,
the annotations were not recognised and validation did not work
. To fix this I added `spring-boot-starter-validation` to `pom.xml` in the
  new project, and used `@Valid` on the request body in `BookingController`. It now works because the dependency provides the validation annotations
, so invalid inputs are rejected using 400 as a response.**

**Problem and correction 2: In my original `Booking` model, `status` and `Count` were
declared as Strings. This meant the attendee count was not a whole number and the
status was not limited to valid values. My `@ManyToOne` relationship also pointed
the wrong way, with a `Booking` field inside the other class instead of a `Room`
inside `Booking`.
To fix this I added `Booking` and `BookingStatus` (ACTIVE, CANCELLED) to
  the `model` package and new
  bookings default to ACTIVE.
It now works because the entity lets bookings be saved and retrieved with their room
  and status, and the enum limits status to valid values.

**Problem and correction 3 (optional): Adding Exception to my code. My original code had no exception handling, so a missing
room, a missing booking, or an over-capacity booking had no meaningful error
response.
To fix this I added `ResourceNotFoundException`, `CapacityExceededException`
  and a `GlobalExceptionHandler` and the checks in
  `BookingService.createBooking` and `getBooking`.
It now works because each problem is now thrown as a specific exception and the handler
  converts it to the right HTTP status (404 for missing, 400 for invalid or over
  capacity) with a clear message.

## 3. API testing and evidence

Use Swagger UI or another HTTP client. For **at least four meaningful checks**, record the request/input, expected result, actual HTTP status/response, and pass/fail. Include unsuccessful cases; a running application alone is not enough.
I Tested using Swagger UI at http://localhost:8081/swagger-ui/index.html#/booking-controller/getBooking
**Test 1 — successful booking request:**  
- Request / input:  'POST /api/bookings' with ("roomId": 1, "organiserName": "Aoife Byrne", "attendeeCount": 10)
- Expected: 201 Created with status ACTIVE
- Actual HTTP status / body:  	
  Response body
  Download
  {
  "id": 1,
  "roomId": 1,
  "roomName": "Lab 201",
  "organiserName": "Aoife Byrne",
  "attendeeCount": 10,
  "status": "ACTIVE"
  }
- Pass / fail: Pass

**Test 2 — invalid input or excessive attendee count:**  
- Request / input:   'POST /api/bookings' with ("roomId": 1, "organiserName": "A", "attendeeCount": 10)
- Expected:  400 Bad Request, with a message that organiser name should be between 2 and 60 characters
- Actual HTTP status / body:  {
  "status": 400,
  "error": "Validation failed",
  "details": {
  "organiserName": "Organiser name must be 2 to 60 characters"
  }
  }
- Pass / fail: Pass

**Test 3 — missing booking or room:**  
- Request / input:  'GET/api/bookings/999'
- Expected:  404 Not Found, Booking not Found with id 999
- Actual HTTP status / body:  {
  "status": 404,
  "error": "Booking not found with id 999"
  }
- Pass / fail:  Pass

**Test 4 — requirement specific to my group:** 
4a: Room Name, ordered by attendee count descending
- Request / input:  GET /api/bookings/search?roomName=Lab 201
- Expected:  200 OK
- Actual HTTP status / body: [
  {
  "id": 2,
  "roomId": 1,
  "roomName": "Lab 201",
  "organiserName": "Sean Murphy",
  "attendeeCount": 22,
  "status": "ACTIVE"
  },
  {
  "id": 3,
  "roomId": 1,
  "roomName": "Lab 201",
  "organiserName": "Niamh Kelly",
  "attendeeCount": 15,
  "status": "ACTIVE"
  },
  {
  "id": 1,
  "roomId": 1,
  "roomName": "Lab 201",
  "organiserName": "Aoife Byrne",
  "attendeeCount": 10,
  "status": "ACTIVE"
  }
  ] 
- Pass / fail:  Pass

4b: No matching bookings
- Request / input:  GET /api/bookings/search?roomName=Nonexistent
- Expected:  200 OK, Empty List
- Actual HTTP status / body: 200
  Response body
  Download
  []
- Pass / fail: Pass

4c: Partial Name
- Request / input:  GET /api/bookings/search?roomName=Lab
- Expected:  200 OK, Empty List
- Actual HTTP status / body:
  Code	Details
  200
  Response body
  Download
  []
- Pass / fail: Pass

**Group A:** Demonstrate `GET /api/bookings/search?roomName=...` using an exact room name, bookings ordered by attendee count descending, and an empty list for no matches. Add checks if necessary to show all three behaviours.

## 4. Reflection

**What did I understand during the examination but struggle to implement?**  During the exam I understood the layout and structure of the application.
I understood what went where and how to use some of the code I knew. What I struggled to use but I knew was returning the Http Status codes.
I also struggled to use the validations annotations I knew which ones to use but they obviously didn't run as my pom.xml file was wrong in which I figured out about 15 minutes after the examination.
I also struggled to understand that I needed exceptions and proper response
handling to get the right status codes back, such as 200, 201 and 404.

**Which Spring Boot, JPA, validation, JPQL or OpenFeign concept became clearer after correcting my code? Give a specific example.**  
Validation became much clearer as in my original code I knew which annotations to use, but they did not work because I did not have the right dependency in my pom.xml file, I was not using spring-boot-starter-validation.
After correcting my code and creating a new project I understood that @Valid on the request body in BookingController is what makes Spring actually check them and make it work.
For Example in BookingRequest it has @Size(min = 2, max = 60) telling me in Swagger that I have to have characters with a name in between this numerical count unlike when I used 'A' for example, and it returned 400 with a message.

**What will I do differently in my next coding test or API development task?**
In my next coding exam I need to slow it down and read through the exam sheet carefully to understand what packages and classes I need.
I need to understand what goes into each class and what each one is for so i can use them with more confidence.
I also need to remember to look at pom.xml file to see if I need to add any new files to allow my code to run.
Finally, I will test my application in Swagger as I go, after each task, instead of waiting until
the end, so I catch problems like a missing dependency straight away. 

## Review lab assessment — 20 marks

- Honest original self-assessment with code evidence: **4 marks**
- Explanation of mistakes and Spring Boot concepts: **6 marks**
- Corrections in the new application: **4 marks**
- API testing and demonstration of understanding: **4 marks**
- Reflection and future improvement: **2 marks**

**Total: 20 marks.** Simply submitting a working solution does not demonstrate the learning required by this review.

## Proposed mark recovery — only if confirmed for CICD-1

If the same approach as OOP is adopted, the review could recover **up to 30% of marks lost** in the original examination, scaled by the review-lab mark.

**Marks recovered = (100 − official original exam mark) × 0.30 × (review lab mark ÷ 20)**

Example: exam **40/100** and review **16/20** → **14.4 recovered marks**, revised exam **54.4/100**. This is **not an approved CICD-1 policy unless confirmed by the lecturer**. The original official exam mark—not your self-awarded grade—is used in the calculation.

## Submission

Commit and push the **new review repository** with the completed README, corrected Spring Boot code and API-test evidence. Submit its GitHub URL on Moodle. **Do not push any changes to the original examination repository or `exam-work` pull request.**
