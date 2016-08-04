# kytE -- Realtime online collaborative text editor
An online text editor inspired by Google Docs, that allowes users to collaborate and create a document. 
The implementation is based on Calton Pu's work on Partial Persistent Structures for online editing (http://bit.ly/2a3EyVG).

On the backend, we designed a distributed server architecture that interacts with replicated BerkleyDB instances to provide low latency with high availability. This involved using the principles of Distributed Transactions and Operational Transformation to provide consistency.
