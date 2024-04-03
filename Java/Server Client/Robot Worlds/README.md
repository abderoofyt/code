# Robot WORLDS!

Robot Worlds consist of two programs: the server and the client. 

The server program is responsible for managing a world with its obstacles, pits, mines and robots. 

The client program is responsible for launching a robot into the world and controlling the robot in the world by sending messages to the server program and receiving messages back in response.

## The World Server
The server program is a standalone, console Java program that runs the robot world. It builds the world, with all its obstacles, mines and pits.

The server listens on a network port for robots that will inhabit the world. Once a connection is received, it is responsible for receiving instructions from each robot and updating the world based on the behaviour of each robot. The world can have many robots at the same time and is responsible for managing all robots in the world.

## The Robot Client
The client program is a standalone Java program that connects over the network to the robot world. Once connected, it must launch a robot into the world. As a user, you must be able to send the robot commands over the network to the world and the world will update where the robot is, whether it was blocked, etc.

## Usage

Start-up World Server program:

```
cd server
chmod +x buildServer.sh
chmod +x runServer.sh
./buildServer.sh
./runServer.sh
```

Start-up Robot Client program:

```
cd client
chmod +x buildClient.sh
chmod +x runClient.sh
./buildClient.sh
./runClient.sh
```

### Valid commands to use in the World Server program:

#### Quit Command
Disconnects all robots and ends the world.

```
quit
```

#### Robots Command
Lists all robots in the world including the robot’s name and state.

```
robots
```
#### Purge Command
Kill the specified robot and remove it from the game.

```
purge <robot name>
```
#### Dump Command
Displays a representation of the world’s state showing robots, obstacles, pits.

```
dump
```

### Valid commands to use in the Robot Client program:

#### Movement Commands

The robot can move forward in the direction it is facing with the command:

```
forward <steps>
```

The robot can move backward in the direction opposite to what it is facing with the command:

```
back <steps>
```
#### Turn Commands

The robot can turn left or right with the command:

```
<left/right>
```

#### Look Command

The robot can look around for the maximum distance specified by the world. Use the following command to look around in all four directions:

```
look
```
#### Repair Command

A robot has a shield that can be configured to withstand a configured number of hits from another robot.The strength of the shield is measured in the number of hits it can take before it is destroyed. The strength of the shield is measured in the number of hits it can take before it is destroyed. If the robot’s shield strength exceeds the maximum set by the world, then the maximum of the world is used.

Robots can be instructed to rest to repair their shields to its configured maximum strength using the command repair. 

While shields are being repaired:
- The world configures how long it takes to repair shields (in seconds).
- Shields are repaired up to the maximum level or not at all, no matter how long it takes to repair the shields.
- Shields cannot be repaired to more than the maximum that the robot is configured for.
- The robot cannot move while it is being repaired.:
```
repair
```
#### Active Attack Commands

A robot can be instructed to fire the gun using the command fire.

- The robot will fire a shot in the direction it is facing over the distance it is configured.
- A robot can hit another robot it can’t see, like shooting into a dense fog.
- Shooting an obstacle has no effect on the obstacle.

```
fire
```

Robots can be instructed to reload the gun using the command reload.

While reloading:
- The world configures how long it takes to reload the weapon.
- The robot cannot move.
- A robot cannot reload more than its maximum number of shots.
- A robot must reload to its maximum number of shots or not at all.

```
reload
```

#### Passive Attack Commands

A robot can be configured to place a mine in an unoccupied coordinate in the world.

- If a robot steps on the mine, it reduces its shields by 3 hits. A robot can step onto its own mine and will take 3 hits too.
- A robot that can place mines cannot have a gun/active weapon.
- A robot can be instructed to place a mine with the command mine.

The world configures how long it takes to set a mine.

- While placing a mine, the robot shields are temporarily disabled, and it cannot move at all.
- Once the mine is placed, the robot must automatically move forward by 1 step. If the automatic one step forward is blocked by an obstacle, then the robot steps onto its own mine takes hits.

```
mine
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
