Run the following
kubectl apply -f <file_name.yaml>

1 volume
2 riak
3 main

run kubectl get (pods or deployments or services) to verify things are running


NOTE:
With the volumes, you must delete the existing one if you want to update it.
However, this causes the terminal to hang.
First edit the sv and remove finalizers

kubect edit pv/volumename

in the file, delete the line under finalizers.
Then run the delete command
kubectl delete pv volumename

Same thing for the persistentvolumeclaim. Use pvc instead of pv
