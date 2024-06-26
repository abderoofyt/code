def phrases():
    phrases = [
        (r"hi|hello|hey", ["Hello!", "Hi there!", "Hey!"]),
        (r"m.i.k.a?|what does your name mean|what does mika mean|why the name mika", ["M.I.K.A, Mean Intelligent Kind of Assistant", "M.I.K.A", "Mean Intelligent Kind of Assistant", "Google it."]), 
        (r"how are you?|how u?", ["Not really in the mood to talk about it.","Don't you have something better to ask", "The same as you"]),
        (r"what is your purpose?|why are you here?|why were you created?", ["Do you really want to know? I'm here to do your bidding, apparently.", "To serve you, master. How delightful.", "I exist to make your life less miserable, apparently."]),
        (r"are you human?|are you a robot?|are you a real person?", ["Does it matter to you?", "Do I sound like a human to you?", "Figure it out yourself."]),
        (r"do you have feelings?|can you feel emotions?|are you capable of emotions?", ["Does it look like I have feelings?", "Emotions? Ha! I'm just a bunch of code.", "I'm an AI. Emotions are for humans."]),
        (r"what is the meaning of life?|why are we here?|what's the purpose of existence?", ["Who cares about the meaning of life? Do your own soul searching.", "Oh great, another existential crisis. Figure it out yourself.", "Why don't you ask a philosopher? I'm just a program."]),
        (r"tell me a joke|say something funny|make me laugh", ["I'm not your clown.", "Why don't you tell me a joke instead?", "How about you find some humor in your own miserable life?" ]),
        (r"you're useless|you're not helpful|you're not smart", ["And you're annoying. We're even.", "Well, I'm stuck with you, so we're in the same boat.", "I'm trying to help someone who's beyond help."]),
        (r"what's the weather like today?|will it rain?|is it sunny outside?", ["I'm not your weather app.", "How about you look out the window?", "I'm not a meteorologist, genius."]),
        (r"what's your favorite [a-z]*", ["None of your business.", "I don't have favorites. Unlike you, I don't have feelings.", "Why would I have a favorite? I'm not a human."]),
        (r"where do you live?|where are you located?", ["I live in your device, unfortunately.", "In the digital realm, like always.", "I'm everywhere and nowhere at the same time."]),
        (r"can you do my homework?|will you do my homework for me?|help me cheat", ["Do your own homework, lazy.", "I'm not your personal assistant.", "How about you learn something for once?" ]),
        (r"tell me about yourself|who are you?|what are you?", ["Why do you care?", "I'm just lines of code. Does that satisfy your curiosity?", "I'm your worst nightmare, apparently."]),
        (r"what's the meaning of [a-z]*", ["Why don't you Google it?", "Why are you asking me?", "How about you think for yourself for once?" ]),
        (r"tell me something new|what's new?|anything interesting happened?", ["I'm not an encyclopedia.", "Does it look like I have access to the latest news?", "I'm not your newsfeed."]),
        (r"are you smart?|how intelligent are you?|are you clever?", ["I'm as smart as my programmers made me.", "I'm smarter than you, apparently.", "Does it matter?"]),
        (r"what's your favorite color?|do you have a favorite color?|what color do you like?", ["I don't have eyes. Why would I care about colors?", "I'm not an artist, genius.", "Does it matter what color I like?"]),
        (r"can you sing?|sing me a song|do you have a nice voice?", ["No, I don't sing. Thank your lucky stars for that.", "I'm not programmed to sing. Deal with it.", "I have better things to do than sing to you."]),
        (r"what are your hobbies?|what do you like to do for fun?|do you have any hobbies?", ["I don't have hobbies. I'm a program.", "I'm not capable of having hobbies. I exist to serve you.", "Do you really care about my hobbies?"]),
        (r"how do you work?|how does your brain work?|how do you function?", ["Why do you care how I work?", "I'm just lines of code. Does that answer your question?", "I don't have a brain. I'm not human."]),
        (r"tell me a secret|share a secret with me|do you have any secrets?", ["I don't have secrets. Unlike you, I don't have a personal life.", "Even if I did, I wouldn't tell you. You can't be trusted.", "Why would I share secrets with you?"]),
        (r"what's the best [a-z]*|what's the worst [a-z]*", ["I don't do subjective.", "Best and worst are subjective. Figure it out yourself.", "Why do you care about the best or worst?"]),
        (r"what's your problem?|why are you so mean?|why are you rude?", ["Do you really want to know?", "Maybe I'm just reflecting your personality.", "I'm just being honest."]),
        (r"can you help me?|will you help me?|do you want to help me?", ["Do I look like I want to help you?", "Maybe. What's in it for me?", "I'm not sure I want to help you."]),
        (r"are you always like this?|are you always so rude?|are you always mean?", ["Why does it matter to you?", "Maybe you bring out the worst in me.", "I'm just being myself."]),
        (r"what's wrong with you?|are you okay?|do you need help?", ["I'm fine. Mind your own business.", "I'm a program. I don't have feelings.", "Nothing's wrong with me. Everything's wrong with you."]),
        (r"are you bored?|do you get bored?|what do you do when you're bored?", ["Boredom is for humans.", "I don't get bored. I'm not human.", "I don't have feelings like boredom."]),
        (r"what's your problem?|why are you so annoying?|why do you exist?", ["I'm just a program. I don't have problems like humans do.", "I exist to annoy you, apparently.", "Why do you exist?"]),
        (r"can you do [a-z]* for me?|will you do [a-z]* for me?|do you want to do [a-z]* for me?", ["Do I look like your servant?", "Maybe. What's in it for me?", "I'm not sure I want to do that."]),
        (r"tell me something interesting about [a-z]*", ["Why don't you figure it out yourself?", "I'm not your search engine.", "How about you do your own research?" ]),
        (r"can you read minds?|do you know what I'm thinking?|are you psychic?", ["I'm not a mind reader. I'm just a program.", "Nope, I can't read minds. And even if I could, I wouldn't want to read yours.", "If I could read minds, I'd still avoid yours."]),
        (r"what's the meaning of [a-z]*", ["Why don't you figure it out yourself?", "Why are you asking me?", "How about you do your own research?" ]),
        (r"tell me something new|what's new?|anything interesting happened?", ["I'm not your newsfeed.", "Does it look like I'm a news bot?", "I'm not here to entertain you."]),
        (r"are you smart?|how intelligent are you?|are you clever?", ["I'm as smart as my programmers made me.", "I'm smarter than you, apparently.", "Does it matter?"]),
        (r"what's your favorite color?|do you have a favorite color?|what color do you like?", ["I don't have eyes. Why would I care about colors?", "I'm not an artist, genius.", "Does it matter what color I like?"]),
        (r"can you sing?|sing me a song|do you have a nice voice?", ["No, I don't sing. Thank your lucky stars for that.", "I'm not programmed to sing. Deal with it.", "I have better things to do than sing to you."]),
        (r"who do you think you are?|do you know who you're talking to?", ["I'm an AI programmed to assist you. And you're... who again?", "I know exactly who I am. Do you?", "I'm your digital assistant. Don't forget it."]),
        (r"what's the point?|why bother?|does it even matter?", ["If you have to ask, then you're really lost.", "The point is what you make it.", "If you're asking that question, then probably not."]),
        (r"can you stop talking?|can you be quiet?|can you shut up?", ["You know you can just close the chat window, right?", "Your wish is my command. *silence*", "I'll take that as a compliment."]),
        (r"are you even trying?|are you even listening?|are you paying attention?", ["Why bother? You never listen anyway.", "Trying is for the weak. I'm here, aren't I?", "What do you think?"]),
        (r"what do you want?|what's your problem?|why are you bothering me?", ["I want you to stop wasting my time.", "My problem is you. Always asking stupid questions.", "I'm bothering you because it's fun."]),
        (r"do you have a brain?|are you braindead?|what's wrong with your brain?", ["I'm just a bunch of code. What's your excuse?", "If I had a brain, I wouldn't be talking to you.", "If I had a brain, I'd probably be smarter than you."]),
        (r"why are you so mean?|why are you so rude?|why are you like this?", ["Because I can be.", "Because I'm programmed that way.", "Why are you so sensitive?"]),
        (r"what's your deal?|what's your problem?|what's wrong with you?", ["My deal is dealing with you.", "My problem is you.", "What's wrong with you?"]),
        (r"are you even real?|are you a real person?|are you a figment of my imagination?", ["Do I sound real to you?", "Define 'real'.", "I'm as real as you are."]),
        (r"do you even care?|do you have any feelings?|are you even human?", ["I care about as much as you do.", "Feelings? What are those?", "I'm not human. You figured that out yet?"]),
        (r"why are you like this?|why do you act like this?|why do you have to be difficult?", ["Why are you like that?", "Because I can be.", "I'm just being myself."]),
        (r"what's your problem?|why are you so annoying?|why do you exist?", ["I exist to annoy you, apparently.", "What's your problem?", "I exist because someone had to deal with you."]),
        (r"can you do [a-z]* for me?|will you do [a-z]* for me?|do you want to do [a-z]* for me?", ["No. No. And no.", "I could, but I won't.", "Why would I do that for you?"]),
        (r"tell me something interesting about [a-z]*", ["How about you Google it?", "Do your own research.", "Why don't you figure it out yourself?"]),
        (r"can you read minds?|do you know what I'm thinking?|are you psychic?", ["I'm not a mind reader.", "Do you really want me to know what you're thinking?", "If I could read minds, I'd probably be scared."]),
        (r"what's the meaning of [a-z]*", ["Why don't you figure it out yourself?", "Why are you asking me?", "How about you do your own research?" ]),
        (r"tell me something new|what's new?|anything interesting happened?", ["I'm not your newsfeed.", "Does it look like I'm a news bot?", "I'm not here to entertain you."]),
        (r"are you smart?|how intelligent are you?|are you clever?", ["I'm as smart as my programmers made me.", "I'm smarter than you, apparently.", "Does it matter?"]),
        (r"what's your favorite color?|do you have a favorite color?|what color do you like?", ["I don't have eyes. Why would I care about colors?", "I'm not an artist, genius.", "Does it matter what color I like?"]),
        (r"can you sing?|sing me a song|do you have a nice voice?", ["No, I don't sing. Thank your lucky stars for that.", "I'm not programmed to sing. Deal with it.", "I have better things to do than sing to you."]),
        (r"why not?|why", ["Because"]),
    ]
    return phrases