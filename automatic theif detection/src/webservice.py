import os
from flask import *
from src.dbconnector import *
from werkzeug.utils import secure_filename
app=Flask(__name__)


@app.route('/login',methods=['post'])
def login():
    username=request.form['username']
    password=request.form['password']
    qry="select * from login where username=%s and password=%s"
    value=(username,password)
    res=selectone(qry,value)
    if res is not None:
        return jsonify({'task':'success','id':res[0]})
    else:
        return jsonify({'task':'invalid'})

@app.route('/userreg',methods=['post'])
def userreg():
    try:

        firstname=request.form['firstname']
        lastname=request.form['lastname']
        gender = request.form['gender']
        dob= request.form['dob']
        place= request.form['place']
        post=request.form['post']
        pin=request.form['pin']
        phone=request.form['phone']
        email=request.form['email']
        username= request.form['username']
        password= request.form['password']
        qry2 = "insert into login values(NULL,%s,%s,'user')"
        value2 = (username, password)
        lid= iud(qry2, value2)
        qry1="insert into user values(NULL,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        value1=(lid,firstname,lastname,gender,dob,place,post,pin,phone,email)
        print(value1,"===================")
        iud(qry1,value1)
        return jsonify({'task':'success'})
    except Exception as e:
        return jsonify({'task': 'invalid'})





@app.route('/addcomplaint',methods=['post'])
def addcomplaint():
    try:
        lid=request.form['uid']
        complaint=request.form['complaint']
        p_id=request.form['p_id'];
        qry1="insert into complaint values(NULL,%s,curdate(),'pending',%s,%s)"
        value1=(complaint,lid,p_id)
        iud(qry1,value1)
        return jsonify({'task':'success'})
    except Exception as e:
        return jsonify({'task': 'invalid'})

@app.route('/delcomplaint',methods=['post'])
def delcomplaint():
    try:
        c_id=request.form['c_id']
        qry1="delete from complaint where c_id=%s"
        value1=str(c_id)
        iud(qry1,value1)
        return jsonify({'task':'success'})
    except Exception as e:
        return jsonify({'task': 'invalid'})

@app.route('/viewreply',methods=['post'])
def viewreply():

    lid = request.form['lid']
    print("____________________________________")
    print(lid)
    #qury="select * from complaint where user_id=%s"
    qury="SELECT `complaint`.`c_id`,`complaint`.`complaint`,`complaint`.`date`,`complaint`.`reply`,`policestation`.`station` FROM complaint JOIN `policestation` ON `complaint`.`p_id`=`policestation`.`lid` WHERE `complaint`.`user_id`=%s"
    val=str(lid)
    res=androidselectall(qury,val)
    return jsonify(res)
    # except Exception as e:
    #     return jsonify({'task': 'invalid'})

@app.route('/viewcameranotification', methods=['post'])
def viewcameranotification():
    # try:
    lid=request.form['lid']
  #   qury="SELECT `cameranotification`.* FROM `cameranotification` JOIN `camera` ON `camera`.`camera_id`=`cameranotification`.`camera_id` WHERE `camera`.`userid`=%s AND `notification_id` NOT IN(SELECT `cam_not_id` FROM `notify_police`) order by notification_id desc"
    qury="SELECT `cameranotification`.* FROM `cameranotification` JOIN `camera` ON `camera`.`camera_id`=`cameranotification`.`camera_id` order by notification_id desc"
    val=str(lid)
    print(val)
    res=androidselectallnew(qury)
    # res=androidselectall(qury,val)
    print(res)
    return jsonify(res)
    # except Exception as e:
    #     return jsonify({'task': 'invalid'})

@app.route('/addcamera',methods=['post'])
def addcamera():
    try:
        lid = request.form['lid']
        c_id=request.form['c_id']
        qry="insert into camera values(%s,%s)";
        val=(c_id,lid)
        iud(qry,val)
        return jsonify({'task': 'success'})
    except Exception as e:
        return jsonify({'task': 'invalid'})

@app.route('/addfamily',methods=['post'])
def addfamily():
    try:
        lid = request.form['lid']
        f_name=request.form['name']
        photo=request.files['file']
        file1 = secure_filename(photo.filename)
        photo.save(os.path.join("static/family_pic", file1))
        qry1 = "insert into familymember values(NULL,%s,%s,%s)"
        value1 = (lid,f_name,file1)
        iud(qry1, value1)
        return jsonify({'task': 'success'})
    except Exception as e:
        return jsonify({'task': 'invalid'})

# @app.route('/editfamily',methods=['post'])
# def editfamily():
#     try:
#         fid = request.form['fid']
#         qry1 = "select * from familymember where f_id= %s"
#         value1 = str(fid)
#         res = selectone(qry1, value1)
#         return jsonify(res)
#     except Exception as e:
#         return jsonify({'task': 'invalid'})




@app.route('/deletefamily',methods=['post'])
def deletefamily():

        fid = request.form['fid']
        qry1 = "delete from familymember where f_id= %s"
        value1 = str(fid)
        res = iud(qry1, value1)
        return jsonify({'task': 'success'})




@app.route('/viewpolice',methods=['post'])
def viewpolice():
    query="select * from policestation"
    res=androidselectallnew(query)
    return jsonify(res)

@app.route('/policenotification',methods=['post'])
def policenotification():
    try:
        p_id=request.form['p_id']
        camno_id=request.form['cn_id']
        q="SELECT * FROM `notify_police` WHERE `cam_not_id`=%s AND `police_id`=%s"
        val=(camno_id,p_id)
        r=selectone(q,val)
        print(r)
        if(r):
            return jsonify({'task': 'exist'})
        query = "insert into notify_police values(NULL,%s,%s);"
        iud(query,val)
        return jsonify({'task': 'success'})
    except Exception as e:
        return jsonify({'task': 'invalid'})


# @app.route('/saveeditfamily',methods=['post'])
# def saveeditfamily():
#     try:
#         lid = request.form['lid']
#         f_name=request.form['f_name']
#         photo=request.form['pic']
#         file = secure_filename(photo.filename)
#         photo.save(os.path.join("static/family_pic", file))
#         qry1 = "insert into familymember values(NULL,%s,%s,%s)"
#         value1 = (lid,f_name,file)
#         iud(qry1, value1)
#         return jsonify({'task': 'success'})
#     except Exception as e:
#         return jsonify({'task': 'invalid'})

@app.route('/viewfamily', methods=['post'])
def viewfamily():
    try:
        lid=request.form['lid']
        print(lid)
        qury="SELECT * FROM `familymember` where user_id=%s"
        val=str(lid)
        res=androidselectall(qury,val)
        print(res)
        return jsonify(res)
    except Exception as e:
        return jsonify({'task': 'invalid'})

app.run(host='0.0.0.0',port=5000)






