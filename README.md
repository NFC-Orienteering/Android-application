I have looked at [Vagrant](https://www.vagrantup.com/) to help with the irritating task of installing Ruby to Windows. It's now only couple of steps away to have your development environment running!



# Steps

0. Install VirtualBox [https://www.virtualbox.org/](https://www.virtualbox.org/)


1. Clone `orienteering_app` repository to your computer.  **[Vagrantfile](https://github.com/NFC-Orienteering/orienteering_app/blob/master/Vagrantfile)** is available in Git repo, it's used to configure the virtual machine. 

2. Install [Vagrant](https://www.vagrantup.com/). 

3. Make sure SSH client is available in your PATH. Easiest way is to include Git binary folder to the PATH, mine was `C:\Program Files (x86)\Git\bin`. You can test if it's working with `ssh -v` , it should print version details...

4. Install **[librarian-chef vagrant plugin](https://github.com/emyl/vagrant-librarian-chef-nochef)**. Command: `vagrant plugin install vagrant-librarian-chef-nochef`

5. `vagrant up`.

    Yeah, it might take **a while**. For me with Core i5 and 3G mobile network it took about 20-30mins. Check Facebook in mean time :) 

   Next time it shouldn't take more than 1-2 minutes, because first time it will download

    + Ubuntu image
    + Chef, Ruby, Postgres, NodeJS etc... 
    + It will download and install all the Gems of the project.

5. When it's finished do `vagrant ssh` and you will log into the virtual machine.

`cd /vagrant` to change directory to the application root, run `rails server` and the server should start! (use `rails server -d` if you want to run the server in background).

You should see this WEBrick stuff when server is running: 

    `==> default: Starting rails server...
     ==> default: [2014-12-21 19:45:58] INFO  WEBrick 1.3.1
     ==> default: [2014-12-21 19:45:58] INFO  ruby 2.1.3 (2014-09-19) [x86_64-linux]
     ==> default: [2014-12-21 19:45:58] INFO  WEBrick::HTTPServer#start: pid=2584 port=3000`

6. Go to [http://localhost:3000](http://localhost:3000) with your browser :) You can edit the source files normally in Windows, they are automatically synced with virtual machine by Vagrant.

7. You can connect to the virtual machine itself with `vagrant ssh`, `cd /vagrant` to the folder where Rails files are found.

# About Vagrant

With [Vagrant](https://www.vagrantup.com/) it's ridiculously easy to share development environment for example with a team having different OSes. You just need to install Vagrant locally, and then include **Vagrant** file in your repository. Type `vagrant up` in folder and you have a virtual machine running. With provisioning it's easy to create scripts which will automatically setup the environment ready from scratch. 

## Commands
`vagrant up` starts up the VM

`vagrant reload --provision` will reload VM and run provision scripts again

`vagrant halt` will shutdown the VM

`vagrant destroy` will destroy the whole VM. `vagrant up` after this command will take time when it's downloading stuff again.

# About Chef & Librarian

[Librarian-chef](https://github.com/applicationsonline/librarian-chef) is used to do Chef provisioning in Vagrant. 

[Chef](https://www.chef.io/chef/) is a configuration management tool, with which you can do "infrastructure as code". You can define 'cookbooks' to automate installation of servers and needed software.

Librarian is a kind of package manager for Chef cookbooks. A **Cheffile** is used to define what cookbooks are used. Librarian will download them to /cookbooks -folder. In **Vagrantfile** we use [Chef solo](https://docs.vagrantup.com/v2/provisioning/chef_solo.html) to deploy cookbooks inside our Vagrant VirtualMachine. For example PostgreSQL server is installed easily with Chef.

    config.vm.provision "chef_solo" do |chef|
    
    chef.cookbooks_path = ["cookbooks", "site-cookbooks"]
    
    chef.add_recipe "apt"
    chef.add_recipe "openssl"
    chef.add_recipe "postgresql"
    chef.add_recipe "ruby_build"
    chef.add_recipe "rbenv::user"
    chef.add_recipe "rbenv::vagrant"
    chef.add_recipe "postgresql::server"
    chef.add_recipe "postgresql::client"
    .
    .
    .

We can use Chef for controlled deployment of the application to production server(s).

# Possible troubles

## Forgot to install _librarian-chef plugin_ for Vagrant before running `vagrant up`
Yeah, this happens. If you did `vagrant up` before installed the librarian-chef plugin, do `vagrant destroy` to shutdown the virtual machine, then `vagrant plugin install vagrant-librarian-chef-nochef` to install the plugin and after that do `vagrant up` again.

## Check line endings of _.ruby-version_ file

When Vagrant was running the _start_ruby.sh_ script I encountered a strange error, which was 

`' is not installed: version 2.1.3`. 

It took me almost 3 hours before I finally understood what was the problem. When you install Git for Windows, Git is by default configured to **convert all line endings to Windows CRLF**. So when you `git pull` it converts all Unix LF line endings to CRLF. This is problematic when running unix scripts, for example in Vagrant's Virtual Machine.

The error message was `' is not installed: version 2.1.3`.  because the virtual machine uses [rbenv](https://github.com/sstephenson/rbenv) to manage Ruby installs. In our repository there is _.ruby-version_ file which tells what version of Ruby to use in this project. So when rbenv tried to read the version number from the file, it got mixed up because _.ruby-version_ file had CRLF line endings, not LF like Unix files should have. Thus the error message `' is not installed: version 2.1.3`...

**How to fix?** Modify git config and set option autocrlf to false. How to [here](https://help.github.com/articles/dealing-with-line-endings/) . 

TL;DR: `git config --global core.autocrlf false` then [refresh repository](https://help.github.com/articles/dealing-with-line-endings/#refreshing-a-repository-after-changing-line-endings).
