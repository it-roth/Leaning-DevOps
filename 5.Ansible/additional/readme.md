

```bash 
ansible-vault --help 

# filename: database-secret.yaml 
ansible-vault encrypt database-secret.yaml 
ansible-vault view database-secret.yaml 

# --ask-become-pass 
# -b , --become 

ansible-playbook -i inventory.ini  \
    test.yaml --ask-vault-pass


ansible-playbook -i inventory.ini  \
    test.yaml --ask-vault-pass
```



```bash 

ansible-galaxy install git+https://github.com/keoKAY/ansible-nfs-role-itp.git,main --roles-path .

```