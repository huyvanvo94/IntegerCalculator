<h2>CMPE 277 Lab 1</h2>

<p> 
An integer calculator that computes only integers on Android. </br>
Target API: <strong>23</strong> </br>
Minimum API: <strong>21</strong></br>
</p>

<p>
<strong>A high level view of my application:</strong></br>

In the project, IntegerCalculatorFragment is the view in which it host the 
xml file that contains the UI view. MainActivity is the controller. When a user 
clicks on a button, the fragment will configure the view and then send the 
message to MainActivity. The MainActivity contains the IntegerCalculatorHandler
that is the logic of the application. It handles the requirement and computes 
the values and send the message back to the activity. These messages are error, 
clear, computed value, and overflow. The MainActivity will than update the UI 
according to the message it recieves from its handler. The observer pattern is 
used to send messages. 

Here is a diagram of my application: </br>

Fragment -> MainActivity -> Handler->MainActivity->Fragment<br>
</p>

</p>
<p>

<a href="https://youtu.be/h3UowTWRk3k">Linked to demo of application</a></br>
In the video I tested the application with the requirements from the lab </br>
Which can be found below.
<h3>Requirements</h3>
</p>



<img src="screenshots/d.png">
<img src="screenshots/e.png">

<h3>Screen Shots from Application</h3>
<img src="screenshots/a.png">
<img src="screenshots/b.png">
<img src="screenshots/c.png">
<img src="screenshots/f.png">

