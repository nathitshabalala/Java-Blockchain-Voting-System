
## Problem Background

When we think about voting, we only consider registering, voting, and determining the winner. We often overlook the procedures, mechanics, and measures needed to ensure fair and accurate voting for every voter and candidate. Voting can range from selecting a class representative to voting for a governing party. Current voting systems rely on traditional methods such as paper ballots and electronic voting machines, which face issues related to security, transparency, and accuracy. Existing measures to address these challenges are inadequate, creating an urgent need for secure and robust systems to eliminate vulnerabilities like tampering, fraud, manipulation, inaccuracy, and miscounting. Paper ballot technologies are not immune to flaws, highlighting the demand for a modernized approach to ensure fair and accurate elections.

## Solution

To address these challenges, I propose implementing a blockchain-based voting system. This system will utilize blockchain's key qualities, such as decentralization and tamper resistance, to overcome the shortcomings of current voting technologies. Voter data, including registration and voting information, will be securely stored on the blockchain, enabling voters to cast their votes using their own devices while maintaining privacy and anonymity. The proposed solution aims to ensure that each vote promotes transparency, security, and protection against tampering or modifications of voter selections.

## Novelty

This approach introduces a new era in the voting process and enhances the stability of existing voting systems. By leveraging blockchain technology, it ensures a nondisruptive distributed ledger of votes, addressing the problems associated with traditional methods. Voters can use their personal devices to cast votes, eliminating the need to visit physical voting stations. This uniqueness lies in creating a secure and decentralized system that prioritizes voter trust, integrity, and fairness in the electoral process.

## Logic and Complexity

This blockchain-based online voting system will be developed using the blockchain jar file provided for encryption, hashing, and proof-of-state for validation and authentication. Voter data will be stored in the blockchain, with each block containing a list of voters and their related information. The system will use lists for storing voter information, stacks for managing vote processing (where votes are pushed into a stack to ensure LIFO), and queues to handle errors that may occur, ensuring accurate votes before they are added to the blockchain.
