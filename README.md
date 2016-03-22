## ziHazelcast
This is my attempt at a direct port of Rob Axtell's 'Zero Intelligence Traders' model, used to introduce agent-based modeling and emergent social phenomena to new students of social complexity at George Mason University. My idea is to create a fair comparisson for how this general structure will work (objects and all) using Hazelcast to scale the model up and out.

### Rob's Model
Axtell's code is designed to put a hundred or so Agent-objects into arrays. The `maxNumberTrades` parameter sets the maximum number of times the simulation will: 
 - pick a random buyer from the `buyers` array 
 - pick a random seller from the `sellers` array
 - if neither of them has already traded, they each select a price at which they are willing to buy/sell
 - if the buy price is higher than the sell price, then they trade 
 - their transaction price gets collected as market data for later analysis
 - if they don't trade, then nothing else happens; the trade is over and nothing gets recorded

The agents continue to attempt trading until the loop reaches the maximum number of trades.  Not all buyers and sellers will be satisfied.

The model demonstrates how market prices can be explained by peoples' behavior and the classical economic 'market price' is revealed to be caused, not by 'the invisible hand' but by individual preferences. Same result; correct explanation. 

### My Version
My first step is really to just demonstrate (discover, really) whether a market size can be expanded to billions of agents. Most critically, I want to find a reliable way to run agent-based simulation models at global population scale. Axtell's ZI Traders model is a nice way to start. 

With more advanced versions of the model, agents should be networked in realistic 'local' economies (small worlds of power-law distributed sizes and connectedness). What happens to the local markets when new/better markets get discovered?  
