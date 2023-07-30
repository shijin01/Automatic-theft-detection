import os
from flask import *
from werkzeug.utils import secure_filename

from src.dbconnector import *
app=Flask(__name__)
app.secret_key="qwww"

import functools

def login_required(func):
    @functools.wraps(func)
    def secure_function():
        if "lid" not in session:
            return render_template('login.html')
        return func()

    return secure_function


@app.route('/logout')
def logout():
    session.clear()
    return redirect('/')

@app.route('/')
def main():
    return render_template('Login.html')

@app.route('/login',methods=['post'])
def login():
    username=request.form['textfield']
    password=request.form['Password']
    qry="select * from login where username=%s and password=%s"
    value=(username,password)
    res=selectone(qry,value)
    if res is None:
        return '''<script>alert("Invalid username or password");window.location='/'</script>'''
    elif res[3]=='Admin':
        session['lid']=res[0]
        return '''<script>alert("Login successfull");window.location='/Admin'</script>'''
    elif res[3]=='Police':
        session['lid'] = res[0]
        return '''<script>alert("Login successfull");window.location='/policehome'</script>'''
    else:
        return '''<script>alert("Invalid");window.location='/'</script>'''


@app.route('/Admin')
def AdminHome():
    return render_template('ADMIN.HTML')

@app.route('/policehome')
def PoliceHome():
    return render_template('Policehome.html')

@app.route('/policemanage')
@login_required
def policemanage():
    qry="select * from policestation"
    res=select(qry)
    return render_template('Policestationmanage.html',val=res)

@app.route('/policeadd',methods=['post'])
@login_required
def policeAdd():
    return render_template('PolicestationAdd.html')

@app.route('/policereg',methods=['post'])
@login_required
def policereg():
    station=request.form['textfield']
    place=request.form['textfield2']
    post = request.form['textfield3']
    pin = request.form['textfield4']
    phone = request.form['phone']
    username=request.form['textfield6']
    password=request.form['textfield7']
    qry2 = "insert into login values(NULL,%s,%s,'Police')"
    value2 = (username, password)
    lid= iud(qry2, value2)
    qry1="insert into policestation values(NULL,%s,%s,%s,%s,%s,%s)"
    value1=(station,place,post,pin,phone,lid)
    iud(qry1,value1)
    return '''<script>alert("successfully updated");window.location='/policemanage#portfolio-section'</script>'''


@app.route('/deletePOLICE',methods=['get'])
@login_required
def delpolice():
    crid=request.args.get('id')
    l_id=request.args.get('lid')
    qry="delete from login where l_id=%s"
    val1=str(l_id)
    iud(qry,val1)
    query="delete from policestation where s_id=%s"
    val=crid
    query1 = "delete from criminal where station=%s"
    val2 = crid
    iud(query,val)
    iud(query1, val2)

    return '''<script>alert("successfully DELETED");window.location='/policemanage#portfolio-section'</script>'''

@app.route('/viewcriminal')
@login_required
def ViewCriminal():
    qry="SELECT * FROM `policestation`"
    res=select(qry)
    return render_template('ViewCriminal.html',val=res)

@app.route('/searchcrml',methods=['post'])
@login_required
def searchcriminal():
    station=request.form['textfield']
    qry='select * from criminal where station=%s'
    vals=station
    res1=selectall(qry,vals)
    qry="SELECT * FROM `policestation`"
    res=select(qry)
    return render_template('ViewCriminal.html',val1=res1,val=res)

@app.route('/viewfeedback')
@login_required
def viewfeedback():
    return render_template('viewfeedback.html')

@app.route('/Addmanagecriminal')
@login_required
def Addmanagecriminal():
    lid=session['lid']
    query="SELECT * FROM `criminal` where station=%s"
    val=str(lid)
    res=selectall(query,val)
    return render_template('criminalmanage.html',val=res)

@app.route('/editcriminal')
@login_required
def editcriminal():
    id=request.args.get('id')
    session['crid'] = id
    query="select * from criminal where c_id=%s"
    val=str(id)
    res=selectone(query,val)
    return  render_template('editcriminal.html',vals=res)

@app.route('/saveeditcrmnl',methods=['post'])
@login_required
def saveeditcrimnal():
    try:
        crid = session['crid']
        name = request.form['textfield']
        place = request.form['textfield2']
        gender = request.form['gender']
        crime = request.form['textfield3']
        details = request.form['textfield4']

        photo = request.files['filephoto']
        file = secure_filename(photo.filename)
        photo.save(os.path.join("static/criminal_pic", file))
        qry1 = "update criminal set name=%s,place=%s,gender=%s,photo=%s,crime=%s,station=%s,details=%s where c_id=%s"
        value1 = (name, place, gender, file, crime,session['lid'],details,crid)
        iud(qry1, value1)
        return '''<script>alert("successfully updated");window.location='/Addmanagecriminal#portfolio-section'</script>'''
    except:

        crid = session['crid']
        name = request.form['textfield']
        place = request.form['textfield2']
        gender = request.form['gender']
        crime = request.form['textfield3']
        details = request.form['textfield4']


        qry1 = "update criminal set name=%s,place=%s,gender=%s,crime=%s,station=%s,details=%s where c_id=%s"
        value1 = (name, place, gender, crime, session['lid'],details,crid)
        iud(qry1, value1)
        return '''<script>alert("successfully updated");window.location='/Addmanagecriminal#portfolio-section'</script>'''


@app.route('/deletecriminal',methods=['get'])
@login_required
def delcriminal():
    crid=request.args.get('id')
    query="delete from criminal where c_id=%s"
    val=crid
    iud(query,val)
    return '''<script>alert("successfully DELETED");window.location='/Addmanagecriminal#portfolio-section'</script>'''

@app.route('/Addcriminal',methods=['post'])
@login_required
def Addcriminal():
    return render_template('addcriminal.html')

@app.route('/regcriminal',methods=['post'])
@login_required
def regcriminal():
    name = request.form['textfield']
    place = request.form['textfield2']
    gender = request.form['gender']
    crime = request.form['textfield3']
    Details = request.form['textfield4']

    photo=request.files['filephoto']
    file=secure_filename(photo.filename)
    photo.save(os.path.join("static/criminal_pic",file))
    qry1 = "insert into criminal values(NULL,%s,%s,%s,%s,%s,%s,%s)"
    value1 = (name, place, gender, file, crime,session['lid'],Details)
    iud(qry1, value1)
    return '''<script>alert("successfully updated");window.location='/Addmanagecriminal#portfolio-section'</script>'''


@app.route('/viewcomplaint')
@login_required
def viewcomplaint():
    p_id=session['lid']
    query="SELECT `complaint`.*,`user`.`first_name`,`user`.`Lastname` FROM  `user` JOIN `complaint` ON `complaint`.`user_id`=`user`.`lid` WHERE `complaint`.`reply`='pending' AND `complaint`.`p_id`=%s"
    res=selectall(query,p_id)
    return render_template('Viewcomplaint.html',val=res)

@app.route('/Sendreply',methods=['get','post'])
@login_required
def sendreply():
    id=request.args.get('id')
    session['cid']=id
    return render_template('Sendreply.html')

@app.route('/reply',methods=['post'])
@login_required
def reply():
    cid=session['cid']
    rply=request.form['textarea']
    query="UPDATE `complaint` SET `complaint`.`reply`=%s WHERE `complaint`.`c_id`=%s"
    value=(rply,cid)
    iud(query,value)
    return '''<script>alert("successfully updated");window.location='/viewcomplaint#portfolio-section'</script>'''

@app.route('/viewcameranotification')
@login_required
def viewcameranotification():
    p_id=session['lid']
    query="SELECT `user`.*,`cameranotification`.*,`camera`.`userid` FROM `cameranotification` JOIN `camera` ON`cameranotification`.`camera_id`=`camera`.`camera_id` JOIN `user` ON `user`.`lid`=`camera`.`userid` JOIN `notify_police` ON `notify_police`.`cam_not_id`=`cameranotification`.`notification_id` WHERE `notify_police`.`police_id`=%s"
    val=str(p_id)
    res=selectall(query,val)
    return render_template('viewcameranotification.html',val=res)

@app.route('/lookoutmanage')
@login_required
def lookoutmange():
    query="select * from `lookoutnotice`"
    res=select(query)
    return render_template('LOOKOUTMANAGE.html',val=res)

@app.route('/deltlookout')
@login_required
def deletelook():
    id=request.args.get('id')
    query="delete from lookoutnotice where l_id=%s"
    val=str(id)
    iud(query,val)
    return '''<script>alert("successfully DELETED");window.location='/lookoutmanage#portfolio-section'</script>'''


@app.route('/addlookout',methods=['post'])
@login_required
def addlookout():
    return render_template('addlookoutnotice.html')

@app.route('/reglookout',methods=['post'])
@login_required
def reglookout():
    topic = request.form['textfield']
    details = request.form['textarea']
    qry1 = "insert into lookoutnotice values(NULL,%s,%s)"
    value1 = (topic,details)
    iud(qry1, value1)
    return '''<script>alert("successfully updated");window.location='/lookoutmanage#portfolio-section'</script>'''

app.run(debug=True)
