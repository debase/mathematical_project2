function probBinomial(nn, m, k)
{
if(k<nn-k) {c=comb(nn,k)} else {c=comb(nn,nn-k)}
p=c*Math.pow(m,k)*Math.pow(1-m,nn-k);
return p;
}

function probPoisson(m, k)
{
p=Math.exp(-m)*Math.pow(m,k)/fac(k);
return p;
}

function fac(n)
{
if (n<=1){return 1};
f=1;
for(i=2;i<=n;i++){f=f*i}
return f
}

function comb(n,k)
{
if (n==k || k==0)
{return 1};
if(k<0 || k>n)
{return 0};
if (k==1)
{return n};
num=n;
for(i=1;i<=k-1;i++){num=num*(n-i)}
return num/fac(k)
}
