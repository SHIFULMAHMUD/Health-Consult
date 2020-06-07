

<?php


if($_SERVER['REQUEST_METHOD']=='POST'){
 //Getting values



 $title =  $_POST['title'];
 $message =  $_POST['message'];
 $token =  $_POST['token'];

 send_notification($title,$message,$token);
}


function send_notification($title,$message,$token)
{
	//echo 'Hello';
define( 'API_ACCESS_KEY', 'AAAAQZL6Vtk:APA91bETaEQxFyD8f7MLp3glb9uuaLNQgRl3qvIKN-Nvp4sGwWs1WZ3xPWZdfeTFEKvc08UvXIte6gXzPd7btGWn06iq26VNEF0wbrVM8cIiRUuZuvepYDQG5ZHwZ2k-UkClOsX9T2dV');
 //   $registrationIds = ;
#prep the bundle
     $msg = array
          (
		'body' 	=> $message,
		'title'	=> $title,

          );
	$fields = array
			(
				'to'		=> $token,
				'notification'	=> $msg
			);
	
	
	$headers = array
			(
				'Authorization: key=' . API_ACCESS_KEY,
				'Content-Type: application/json'
			);
#Send Reponse To FireBase Server	
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		echo $result;
		curl_close( $ch );
}
?>