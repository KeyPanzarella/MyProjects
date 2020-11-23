/***************************************************


                Made solely by Key


//**************************************************/

//MUST BE GIVIEN ADMIN PERMISSIONS (or just permission to grant roles)

var Discord = require('discord.js');
var Cbot = new Discord.Client();
var discordServer;
var mArray = new Array();
var cArray = new Array();
var rArray = new Array();
var rolesToAdd = new Array();
var proceed = false;
var greeting; 

var unfEsportsClubDiscID = 363875675880292352;
var botHomeDiscID = 496839982430814209;
var botID = 496835381887631375;





//Updates each array of channels
function updateArrays(){
    console.log('\n-----------\tUPDATED ARRAYS:\t-----------');
    mArray = discordServer.members.array();
    cArray = discordServer.channels.array();
    rArray = discordServer.roles.array();
}

//logs current contents of server statistic arrays
function logServerAttributes(){
    console.log('\n-----------Members in this server:');
    for(var i = 0; i < mArray.length; i++){console.log('\t\t'+mArray[i].displayName);}
    console.log('\n-----------Channels in this server:');
    for(var i = 0; i < cArray.length; i++){console.log('\t\t'+cArray[i].name);}
    console.log('\n-----------Roles in this server:');
    for(var i = 0; i < rArray.length; i++){console.log('\t\t'+rArray[i].name);}
}

//looks for role keywords that immediately follow '!'
function testFollow(follow){
    if(follow.startsWith('unfstudent')){
        console.log('We will try to add Student role here');
        rolesToAdd.push(rArray.find(x => x.name == 'UNF Students'));
    }else if(follow.startsWith('visitor')){
        console.log('We will try to add Visitor role here');
        rolesToAdd.push(rArray.find(x => x.name == 'Visitors'));
    }else if(follow.startsWith('alumni')){
        console.log('We will try to add Alumni role here');
        rolesToAdd.push(rArray.find(x => x.name == 'Alumni'));
    }else if(follow.startsWith('d&d')){
        console.log('We will try to add D&D role here');
        rolesToAdd.push(rArray.find(x => x.name == 'D&D'));
    }else if(follow.startsWith('pubg')){
        console.log('We will try to add PUBG role here');
        rolesToAdd.push(rArray.find(x => x.name == 'PUBG'));
    }else if(follow.startsWith('overwatch')){
        console.log('We will try to add Overwatch role here');
        rolesToAdd.push(rArray.find(x => x.name == 'Overwatch'));
    }else if(follow.startsWith('counter strike')){
        console.log('We will try to add Counter Strike role here');
        rolesToAdd.push(rArray.find(x => x.name == 'Counter Strike'));
    }else if(follow.startsWith('fighters')){
        console.log('We will try to add Fighters role here');
        rolesToAdd.push(rArray.find(x => x.name == 'Fighters'));
    }else if(follow.startsWith('rocket league')){
        console.log('We will try to add Rocket League role here');
        rolesToAdd.push(rArray.find(x => x.name == 'Rocket League'));
    }else if(follow.startsWith('hearthstone')){
        console.log('We will try to add Hearthstone role here');
        rolesToAdd.push(rArray.find(x => x.name == 'Hearthstone'));
    }else if(follow.startsWith('minecraft')){
        console.log('We will try to add Minecraft role here');
        rolesToAdd.push(rArray.find(x => x.name == 'Minecraft'));
    }else if(follow.startsWith('league of legends')){
        console.log('We will try to add League of Legends role here');
        rolesToAdd.push(rArray.find(x => x.name == 'League of Legends'));
    }else if(follow.startsWith('heroes of the storm')){
        console.log('We will try to add Heroes of the Storm role here');
        rolesToAdd.push(rArray.find(x => x.name == 'Heroes of the Storm'));
    }else if(follow.startsWith('fortnite')){
        console.log('We will try to add Fortnite role here');
        rolesToAdd.push(rArray.find(x => x.name == 'Fortnite'));
    }else if(follow.startsWith('rainbow 6 siege')){
        console.log('We will try to add Rainbow 6 Siege role here');
        rolesToAdd.push(rArray.find(x => x.name == 'Rainbow 6 Siege'));
    }
}

//gives new member roles requested (that were stored in an array earlier) and then clears the rolesToAdd array
function grantRoles(mem){
    mem.addRoles(rolesToAdd);
    logAddedRoles(mem);
    popRoleArray();
    //proceed = false;
}

function logAddedRoles(mem){
    var r = '';
    for(var i = 0; i < rolesToAdd.length; i++){
        r += ' ' + rolesToAdd[i].name;
    }
    console.log('Successfully added roles' + r + ' to ' + mem.displayName);
    r = '';
    cArray.find(x => x.name == 'game-check').send('Okay, you\'re all checked in. Feel free to talk in '+
    cArray.find(x => x.name == 'general-chat') + ' and PM any mods with any questions you may have. '+
    'Thanks for joining and welcome to the club!');
}

function popRoleArray(){
    for(var i = 0; i <= rolesToAdd.length; i++){
        rolesToAdd.pop();
    }
    //rolesToAdd = new Array();
}

//only assists if given the thumbs-up by the "proceed" parameter
async function assistMember(proceed, memID){
    greeting = 'Hey there <@' + memID + '>' +
    '! It appears the Mods/Admins are very busy right now' + 
    ' so I\'m here to help you out. \nIn order for me to give you the roles you need to communicate with'+
    ' the rest of the server, I need you to specify whether you are a UNF student (with the command ***!UNFStudent***) or'+
    ' an alumni (***!Alumni***) or just a visitor (***!Visitor***) and tell me which games you like to play from our'+
    ' list of game roles from the #welcome list. \n(Example: \"Hey, I play ***!Overwatch !Rocket League and !League of Legends***' +
    ' and I\'m just a ***!Visitor***\")';

    if(proceed){
        cArray.find(x => x.name == 'game-check').send(greeting);
        console.log('\n\n************ Offered assistance to the new member\t'+new Date());
    }else{console.log('\n\n************ Mod or Admin has reacted to new member in under standard wait time\t'+new Date());}
}


//all update-type events purely for updating my arrays
Cbot.on('channelCreate', channel => {updateArrays();logServerAttributes();      console.log('\n\n************ CHANNEL CREATED:\t'+channel.name+'\t'+new Date());});
Cbot.on('channelDelete', channel => {updateArrays();logServerAttributes();      console.log('\n\n************ CHANNEL DELETED:\t'+channel.name+'\t'+new Date());});
Cbot.on('guildMemberAdd', member => {updateArrays();logServerAttributes();      console.log('\n\n************ NEW MEMBER ADDED:\t'+member.displayName+'\t'+new Date());});
Cbot.on('guildMemberRemove', member => {updateArrays();logServerAttributes();   console.log('\n\n************ MEMBER REMOVED OR HAS LEFT:\t'+member.displayName+'\t'+new Date());});
Cbot.on('messageDelete', message => {updateArrays();logServerAttributes();      console.log('\n\n************ MESSAGE DELETED:\t'+message.author.displayName+': '+message+'\t'+new Date());});
Cbot.on('roleCreate', role => {updateArrays();logServerAttributes();            console.log('\n\n************ ROLE CREATED:\t'+role.name+'\t'+new Date());});
Cbot.on('roleDelete', role => {updateArrays();logServerAttributes();            console.log('\n\n************ DELETED:\t'+role.name+'\t'+new Date());});
Cbot.on('roleUpdate', role => {updateArrays();logServerAttributes();            console.log('\n\n************ ROLE UPDATED:\t'+role.name+'\t'+new Date());});

//checks to see if mod or admin responds to new member. If so, the bot will not do or say anything.
Cbot.on('message', message => {
    //------------------------------START OF GAME-CHECK CHANNEL HANDLING------------------------------
    if(!message.author.bot){
        if(message.channel.name == 'game-check'){
            var positionRole = message.member.hoistRole;
            var noRoles = false;
            if(positionRole == null){
                noRoles = true;
            }//WORK ON THIS PART
            
            if(positionRole.comparePositionTo(rArray.find(x => x.name == 'Mods')) >= 0){
                console.log('\n\n************ AUTHORITY HAS SPOKEN');
                proceed = false;
                //maybe react to message with thumbs up or smiley face
            }
            else if(proceed){
                var msg = message.content.toLowerCase();
                var follow = '';
                for(var i = 0; i < msg.length; i++){
                    if(msg.charAt(i) == '!'){
                        follow = msg.substring(i+1);
                        testFollow(follow);
                    }
                }
                try{
                    grantRoles(message.member);
                }catch(err){
                    console.log(message.member.displayName+' might have already had one of the roles they requested');
                }
            }
        }
    //-------------------------------END OF GAME-CHECK CHANNEL HANDLING-------------------------------

    }
});

//checks for new member and bot will respond after timer is up (as long as proceed is true)
Cbot.on('guildMemberAdd', member => {
    proceed = true;
    setTimeout(function(){assistMember(proceed, member.id);}, (.25*60000));        //change time here
    
});


//handles when the bot mysteriously stops running?/gets disconnected?
Cbot.on('error', () =>{
    console.log('\n\nweird error just occured???\t' + new Date());
});

//defines server the bot works with and builds server statistic arrays
Cbot.on('ready', () => {
    //discordServer = Cbot.guilds.find(val => val.name === 'UNF Esports Club');       //change this name for testing
    discordServer = Cbot.guilds.find(val => val.name === 'BotHome'); 
    console.log('\n\n-----------CinnamonRoleBot ONLINE in '+ discordServer);
    console.log('\n-----------Current Member Count:  ' + discordServer.memberCount);
    mArray = discordServer.members.array();
    cArray = discordServer.channels.array();
    rArray = discordServer.roles.array();
    logServerAttributes();
});

Cbot.login('').catch(console.error);
