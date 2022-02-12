"""
    Only tested under WSL/Linux
    Pre-req:
        pip install scp
        pip install paramiko

    Usage:
        py deployment.py <PAMPERO_SSH_USER> <PAMPERO_SSH_PW> <WAR_PATH>
"""

import paramiko
import sys
import time
import os
from scp import SCPClient


SSH_USERNAME=sys.argv[1]
SSH_PASSWORD=sys.argv[2]
WAR_PATH=sys.argv[3]

SSH_HOST='pampero.itba.edu.ar'
SSH_PORT='22'
HOME_PATH='/home/' + SSH_USERNAME

#  paw-2021b-4@10.16.1.110
SFTP_HOST='10.16.1.110'
SFTP_PORT='22'
SFTP_USER='paw-2021b-4'
SFTP_PASSWORD='yy8tEQt0e'
SFTP_DEPLOY_DIR=SFTP_HOST + '/web/'


print('Deploying war...')

command = 'curl -k "scp://{}/{}/app.war" --user "{}:{}" -T {}'.format(SSH_HOST, HOME_PATH, SSH_USERNAME, SSH_PASSWORD, WAR_PATH)
print("\t=> uploading war to ssh..")
os.system(command)
ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
ssh.load_system_host_keys()
ssh.connect(hostname    = SSH_HOST, 
            port        = SSH_PORT,
            username    = SSH_USERNAME,
            password    = SSH_PASSWORD)
print("\t=> uploaded")
print("\t=> uploading war to sftp..")
command = 'curl  -k "sftp://{}" --user "{}:{}" -T "{}/app.war"'.format(SFTP_DEPLOY_DIR, SFTP_USER, SFTP_PASSWORD, HOME_PATH)
ssh.exec_command(command)
print("\t=> uploaded")
print('War deployed')





